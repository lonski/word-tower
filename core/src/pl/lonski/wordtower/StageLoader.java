package pl.lonski.wordtower;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

class StageLoader {

	private final Dictionary dictionary;
	private final WorldManager worldManager;

	StageLoader(Dictionary dictionary, WorldManager world) {
		this.dictionary = dictionary;
		this.worldManager = world;
	}

	public PlayStage load(FileHandle levelFile) {
		return new PlayStage(createWords(readLevelFile(levelFile)));
	}

	private List<Word> createWords(List<String> lines) {
		List<Word> words = new ArrayList<>();

		float symbolWidth = (float) Gdx.graphics.getWidth() / (float) lines.get(0).length();
		float yOffset = 0;

		for (String line : lines) {

			float xOffset = 0;
			boolean inWord = false;
			int wordLength = 0;

			for (char ch : line.toCharArray()) {
				if (ch == '[') {
					inWord = true;
					wordLength = 0;
				}
				if (ch == ']') {
					inWord = false;
					Word word = new Word(
							worldManager.getWorld(),
							dictionary.getRandomWord(wordLength - 1),
							xOffset,
							yOffset
					);
					words.add(word);
					xOffset += word.getWidth();
				}
				if (!inWord) {
					xOffset += symbolWidth;
				}
				wordLength++;
			}
			if (!words.isEmpty()) {
				yOffset += words.get(words.size() - 1).getHeight();
			}
		}

		return words;
	}

	private List<String> readLevelFile(FileHandle levelFile) {
		List<String> lines = Arrays.asList(levelFile.readString().split("\n"));
		Collections.reverse(lines);
		return lines;
	}


}
