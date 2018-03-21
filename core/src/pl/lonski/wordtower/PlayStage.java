package pl.lonski.wordtower;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

class PlayStage extends Stage {

	private Label wordCounter;
	private int wordsTyped;
	private Label.LabelStyle hudLabelStyle;
	private List<Word> words;

	PlayStage(List<Word> words) {
		this.words = words;
		this.words.forEach(this::addActor);

		Skin skin = SkinProvider.getSkin();
		this.hudLabelStyle = new Label.LabelStyle(skin.getFont("currier-font"), Color.BLUE);

		createWordsCounter();
	}

	private void createWordsCounter() {
		wordCounter = new Label("Words typed: 0", hudLabelStyle);
		wordCounter.setPosition(10, Gdx.graphics.getHeight() - wordCounter.getHeight() - 10);
		addActor(wordCounter);
	}

	private void incWordCounter() {
		wordsTyped++;
		wordCounter.setText("Words typed: " + wordsTyped);
	}

	@Override
	public boolean keyTyped(char character) {
		for (Word word : words) {
			word.eatCharacter(character);
			if (word.isCompleted()) {
				removeWord(word);
				incWordCounter();
				break;
			}
		}
		return true;
	}

	private void removeWord(Word word) {
		word.remove();
		words.remove(word);
		words.forEach(Word::reset);
	}
}
