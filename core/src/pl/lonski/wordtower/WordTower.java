package pl.lonski.wordtower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class WordTower extends ApplicationAdapter {

	private WorldManager world;
	private Dictionary dictionary;
	private StageLoader loader;
	private PlayStage stage;

	@Override
	public void create() {
		world = new WorldManager();
		dictionary = new Dictionary(Gdx.files.internal("assets/words.txt"));
		loader = new StageLoader(dictionary, world, loadSkin());
		stage = loader.load("test.lev");
		Gdx.input.setInputProcessor(stage);
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
	}

	private Skin loadSkin() {
		Skin skin = new Skin(Gdx.files.internal("assets/skins/visui/uiskin.json"));
		skin.add(
				"currier-font",
				new BitmapFont(Gdx.files.internal("assets/fonts/currier.fnt")),
				BitmapFont.class
		);
		return skin;
	}

	@Override
	public void dispose() {
	}

}
