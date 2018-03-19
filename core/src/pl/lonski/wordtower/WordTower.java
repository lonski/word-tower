package pl.lonski.wordtower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import pl.lonski.wordtower.actor.Word;

public class WordTower extends ApplicationAdapter {

	private World world;
	private Stage stage;
	private Box2DDebug physicsDebug;

	@Override
	public void create() {
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();

		world = new World(new Vector2(0, -100), true);
		createFloor();

		Skin skin = new Skin(Gdx.files.internal("assets/skins/visui/uiskin.json"));
		skin.add(
				"currier-font",
				new BitmapFont(Gdx.files.internal("assets/fonts/currier.fnt")),
				BitmapFont.class
		);

		stage = new Stage();
		Gdx.input.setInputProcessor(new InputMultiplexer(stage));

		stage.addActor(
				new Word(world, skin, "Today is monday. LOL!", (int) (screenWidth * 0.5), screenHeight - 50)
		);
		stage.addActor(
				new Word(world, skin, "pysia", (int) (screenWidth * 0.45), screenHeight - 250)
		);

		physicsDebug = new Box2DDebug();
	}


	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f), 6, 2);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

		stage.draw();
//		physicsDebug.render(world);
	}

	private void createFloor() {
		BodyDef floorDef = new BodyDef();
		floorDef.type = BodyDef.BodyType.StaticBody;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		floorDef.position.set(0, h / 2);
		FixtureDef fixtureDef3 = new FixtureDef();

		EdgeShape edgeShape = new EdgeShape();
		edgeShape.set(0, -h / 2, w, -h / 2);
		fixtureDef3.shape = edgeShape;

		Body floor = world.createBody(floorDef);
		floor.createFixture(fixtureDef3);
		edgeShape.dispose();
	}

	@Override
	public void dispose() {
	}

}
