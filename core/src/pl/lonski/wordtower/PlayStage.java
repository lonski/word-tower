package pl.lonski.wordtower;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

class PlayStage extends Stage {

	private PlayerData playerData;
	private Label wordCounter;
	private Label wordsLost;
	private Label.LabelStyle hudLabelStyle;
	private List<Word> words;

	PlayStage(List<Word> words, WorldManager worldManager) {
		this.words = words;
		this.words.forEach(this::addActor);

		Skin skin = SkinProvider.getSkin();
		this.hudLabelStyle = new Label.LabelStyle(skin.getFont("currier-font"), Color.BLUE);

		createWordsCounter();
		createLostWordsCounter();

		worldManager.setFloorCollisionHandler(body -> {
			words.stream().filter(w -> w.getBody() == body).findFirst().ifPresent(this::looseWord);
		});
	}

	int getRemainingWordsCount() {
		return words.size();
	}

	private void createWordsCounter() {
		wordCounter = new Label("Words typed: 0", hudLabelStyle);
		wordCounter.setPosition(10, Gdx.graphics.getHeight() - wordCounter.getHeight() - 10);
		addActor(wordCounter);
	}

	private void createLostWordsCounter() {
		wordsLost = new Label("Words lost: 0", hudLabelStyle);
		wordsLost.setPosition(10, Gdx.graphics.getHeight() - wordsLost.getHeight() * 2 - 10);
		addActor(wordsLost);
	}

	@Override
	public boolean keyTyped(char character) {
		for (Word word : words) {
			word.eatCharacter(character);
			if (word.isCompleted()) {
				scoreWord(word);
				break;
			}
		}
		return true;
	}

	private void scoreWord(Word word) {
		word.remove();
		words.remove(word);
		words.forEach(Word::reset);
		playerData.setWordsTyped(playerData.getWordsTyped() + 1);
		updateLabels();
	}

	private void looseWord(Word word) {
		word.remove();
		words.remove(word);
		playerData.setWordsMissed(playerData.getWordsMissed() + 1);
		updateLabels();
	}

	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
		updateLabels();
	}

	private void updateLabels() {
		wordCounter.setText("Words typed: " + playerData.getWordsTyped());
		wordsLost.setText("Words lost: " + playerData.getWordsMissed());
	}
}
