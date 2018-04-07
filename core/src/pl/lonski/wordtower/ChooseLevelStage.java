package pl.lonski.wordtower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

class ChooseLevelStage extends Stage {

	private final WordTower game;

	ChooseLevelStage(WordTower game) {
		this.game = game;
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();

		new LevelsList().getFileNames().stream()
				.map(name -> new Word(game.getWorldManager().getWorld(), name, screenWidth / 2, screenHeight / 2))
				.forEach(this::addActor);
	}

	@Override
	public boolean keyTyped(char character) {
		for (Actor actor : getActors()) {
			Word word = (Word) actor;
			word.eatCharacter(character);
			if (word.isCompleted()) {
				game.startLevel(word.getText());
			}
		}
		return true;
	}
}
