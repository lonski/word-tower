package pl.lonski.wordtower;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;

class PlayStage extends Stage {

	private List<Word> words;

	PlayStage(List<Word> words) {
		this.words = words;
		this.words.forEach(this::addActor);
	}

	@Override
	public boolean keyTyped(char character) {
		for (Word word : words) {
			word.eatCharacter(character);
			if (word.isCompleted()) {
				removeWord(word);
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
