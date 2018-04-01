package pl.lonski.wordtower;

import java.util.*;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class StageLoader {

	private final Dictionary dictionary;
	private final WorldManager worldManager;

	StageLoader(Dictionary dictionary, WorldManager world) {
		this.dictionary = dictionary;
		this.worldManager = world;
	}

	PlayStage load(FileHandle file) {
		return load(readLevelFile(file));
	}

	public PlayStage load(String level) {
		List<Word> words = createWords(Arrays.asList(level.split("\n")));
		worldManager.update(1 / 30f); //single update to prevent collision detection of the words on the floor
		return new PlayStage(words, worldManager);
	}

	private List<Word> createWords(List<String> lines) {
		Collections.reverse(lines);
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
					xOffset += word.getWidth() + 0.1;
				}
				if (!inWord && ch == '.') {
					xOffset += symbolWidth;
				}
				wordLength++;
			}
			if (!words.isEmpty()) {
				yOffset += words.get(words.size() - 1).getHeight() + 0.1;
			}
		}

		return words;
	}

	private String readLevelFile(FileHandle levelFile) {
		List<String> lines = Arrays.asList(levelFile.readString().split("\n"));
		return lines.stream().collect(Collectors.joining("\n"));
	}

}
