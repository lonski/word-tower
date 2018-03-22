package pl.lonski.wordtower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class WordTower extends ApplicationAdapter {

	private WorldManager world;
	private Dictionary dictionary;
	private StageLoader loader;
	private PlayStage stage;
	private StageIterator iterator;

	@Override
	public void create() {
		world = new WorldManager();
		dictionary = new Dictionary(Gdx.files.internal("words.txt"));
		loader = new StageLoader(dictionary, world);
		iterator = new PredefinedStageIterator(loader, 2);
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
		}
	}

	private void changeStage(PlayStage stage) {
		this.stage = stage;
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
	}

}
