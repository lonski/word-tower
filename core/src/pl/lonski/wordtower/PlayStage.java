package pl.lonski.wordtower;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PlayStage extends Stage {

	private PlayerData playerData;
	private Label score;
	private Label wordCounter;
	private Label wordsLost;
	private Label.LabelStyle hudLabelStyle;
	private Label.LabelStyle scoreLabelStyle;
	private Label.LabelStyle bonusLabelStyle;
	private List<Word> words;

	PlayStage(List<Word> words, WorldManager worldManager) {
		this.words = words;
		this.words.forEach(this::addActor);

		Skin skin = SkinProvider.getSkin();
		this.hudLabelStyle = new Label.LabelStyle(skin.getFont("currier-font"), Color.PURPLE);
		this.scoreLabelStyle = new Label.LabelStyle(skin.getFont("currier-font"), Color.PINK);
		this.bonusLabelStyle = new Label.LabelStyle(skin.getFont("currier-font"), Color.YELLOW);

		createWordsCounter();
		createLostWordsCounter();
		createScoreLabel();

		worldManager.setFloorCollisionHandler(
				body -> words.stream().filter(w -> w.getBody() == body).findFirst().ifPresent(this::looseWord));
	}

	int getRemainingWordsCount() {
		return words.size();
	}

	private void createScoreLabel() {
		score = new Label("Score: 0", scoreLabelStyle);
		score.setPosition(10, Gdx.graphics.getHeight() - score.getHeight() - 10);
		addActor(score);
	}

	private void createWordsCounter() {
		wordCounter = new Label("Words typed: 0", hudLabelStyle);
		wordCounter.setPosition(10, Gdx.graphics.getHeight() - wordCounter.getHeight() * 2 - 10);
		addActor(wordCounter);
	}

	private void createLostWordsCounter() {
		wordsLost = new Label("Words lost: 0", hudLabelStyle);
		wordsLost.setPosition(10, Gdx.graphics.getHeight() - wordsLost.getHeight() * 3 - 10);
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
		int score = word.getSize();
		int bonus = (int) words.stream()
				.filter(w -> w.getY() > word.getY())
				.filter(w -> (word.getX() + word.getWidth()) > w.getX() && word.getX() < (w.getX() + w.getWidth()))
				.mapToInt(Word::getSize)
				.map(size -> Math.max(size / 4, 1))
				.count();

		if (bonus > 0) {
			addActor(new BonusLabel(bonus, word));
		}

		playerData.addScore(score + bonus);
		playerData.incWordsTyped();

		word.remove();
		words.remove(word);
		words.forEach(Word::reset);

		updateLabels();
	}

	private void looseWord(Word word) {
		word.remove();
		words.remove(word);
		playerData.incWordsMissed();
		updateLabels();
	}

	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
		updateLabels();
	}

	private void updateLabels() {
		score.setText("Score: " + playerData.getScore());
		wordCounter.setText("Words typed: " + playerData.getWordsTyped());
		wordsLost.setText("Words lost: " + playerData.getWordsMissed());
	}

	class BonusLabel extends Label {

		BonusLabel(int bonus, Actor word) {
			super(bonus + " bonus!", bonusLabelStyle);
			setX(word.getX() + RandomUtils.nextInt((int) word.getWidth()) * (RandomUtils.nextBoolean() ? 1 : -1));
			setY(word.getY() + RandomUtils.nextInt((int) word.getHeight()) * (RandomUtils.nextBoolean() ? 1 : -1));
			addAction(sequence(fadeOut(0.001f), fadeIn(0.2f), fadeOut(1f), removeActor()));
		}
	}
}
