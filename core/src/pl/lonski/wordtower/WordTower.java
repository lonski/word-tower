package pl.lonski.wordtower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class WordTower extends ApplicationAdapter {

	private WorldManager world;
	private Stage currentStage;
	private PlayStage play;
	private StageIterator iterator;
	private PlayerData playerData;

	public void startGame(StageIterator iterator) {
		world = new WorldManager();
		playerData = new PlayerData();
		Dictionary dictionary = new Dictionary(Gdx.files.internal("words.txt"));
		iterator.initialize(new StageLoader(dictionary, world));
		this.iterator = iterator;
		nextLevel(iterator.next());
	}

	public WorldManager getWorldManager() {
		return world;
	}

	@Override
	public void create() {
		world = new WorldManager();

		changeStage(new MenuStage(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);

		world.update(delta);
		currentStage.act(delta);

		currentStage.draw();
		//world.debugDraw();

		if (currentStage instanceof PlayStage && play.getRemainingWordsCount() == 0) {
			nextLevel(iterator.next());
		}
	}

	private void changeStage(Stage stage) {
		currentStage = stage;
		Gdx.input.setInputProcessor(stage);
	}

	private void nextLevel(PlayStage stage) {
		this.play = stage;
		stage.setPlayerData(playerData);
		changeStage(stage);
	}

	@Override
	public void dispose() {
	}

}
