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
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		createWall(0, 0, w, 1); //floor
		createWall(0, 0, 1, h); //left
		createWall(w, 0,1, h); //right

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

	private void createWall(float x, float y, float hx, float hy) {
		BodyDef floorDef = new BodyDef();
		floorDef.type = BodyDef.BodyType.StaticBody;

		floorDef.position.set(x, y);
		FixtureDef fixtureDef3 = new FixtureDef();

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hx, hy);
		fixtureDef3.shape = shape;

		Body floor = world.createBody(floorDef);
		floor.createFixture(fixtureDef3);
		shape.dispose();
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
