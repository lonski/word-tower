package pl.lonski.wordtower;

import java.security.SecureRandom;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import pl.lonski.wordtower.actor.Word;
import pl.lonski.wordtower.physic.WorldManager;

public class StageLoader {

	private final WorldManager worldManager;
	private final Skin skin;

	private Stage stage;
	private List<Word> words;

	public StageLoader(WorldManager world, Skin skin) {
		this.worldManager = world;
		this.skin = skin;
	}

	public Stage load(String name) {
		createWords(readLevelFile(name));
		createStage();
		configureInputProcessor();

		return stage;
	}

	private void createWords(List<String> lines) {
		this.words = new ArrayList<>();

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
							skin,
							randomString(wordLength - 1),
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
	}

	private void createStage() {
		this.stage = new Stage();
		words.forEach(stage::addActor);
	}

	private void configureInputProcessor() {
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		words.forEach(inputMultiplexer::addProcessor);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	private List<String> readLevelFile(String name) {
		List<String> lines = Arrays.asList(
				Gdx.files.local("assets/levels/" + name)
						.readString()
						.split("\n")
		);
		Collections.reverse(lines);
		return lines;
	}

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		return sb.toString();
	}

}
