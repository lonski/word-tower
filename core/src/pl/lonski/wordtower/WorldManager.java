package pl.lonski.wordtower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

class WorldManager {

	private World world;
	private Box2DDebug debugRender;

	WorldManager() {
		world = new World(new Vector2(0, -100), true);
		createFloor();
		debugRender = new Box2DDebug();
	}

	World getWorld() {
		return world;
	}

	void update(float delta) {
		world.step(delta, 6, 2);
	}

	void debugDraw() {
		debugRender.render(world);
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

	private static class Box2DDebug {

		private OrthographicCamera camera;
		private Box2DDebugRenderer debugRenderer;

		Box2DDebug() {
			camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			camera.setToOrtho(false);
			debugRenderer = new Box2DDebugRenderer();
		}

		void render(World world) {
			camera.update();
			debugRenderer.render(world, camera.combined);
		}
	}
}
