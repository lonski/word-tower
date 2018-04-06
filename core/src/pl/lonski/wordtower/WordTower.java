package pl.lonski.wordtower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import pl.lonski.wordtower.generators.GeneratedStageIterator;

public class WordTower extends ApplicationAdapter {

	private WorldManager world;
	private PlayStage stage;
	private StageIterator iterator;
	private PlayerData playerData;
	private StageLoader loader;

	@Override
	public void create() {
		world = new WorldManager();
		Dictionary dictionary = new Dictionary(Gdx.files.internal("words.txt"));
		loader = new StageLoader(dictionary, world);
		iterator = new PredefinedStageIterator(loader);
		playerData = new PlayerData();
		changeStage(iterator.next());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);

		world.update(delta);
		stage.act(delta);

		stage.draw();
//		world.debugDraw();

		if (stage.getRemainingWordsCount() == 0) {
			changeStage(iterator.next());
			if (iterator instanceof PredefinedStageIterator) {
				if (((PredefinedStageIterator) iterator).isLastLevel()) {
					iterator = new GeneratedStageIterator(loader);
				}
			}
		}
	}

	private void changeStage(PlayStage stage) {
		this.stage = stage;
		stage.setPlayerData(playerData);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
	}
}
