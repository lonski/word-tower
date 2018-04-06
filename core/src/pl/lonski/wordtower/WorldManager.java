package pl.lonski.wordtower;

import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

class WorldManager {

	public static final float PIXELS_TO_METERS = 10f;

	private World world;
	private Box2DDebug debugRender;

	private Body floor;
	private Body ceil;
	private Body leftWall;
	private Body rightWall;

	WorldManager() {
		this(new Vector2(0, -10));
	}

	WorldManager(Vector2 gravity) {
		world = new World(gravity, true);
		float w = Gdx.graphics.getWidth() / PIXELS_TO_METERS;
		float h = Gdx.graphics.getHeight() / PIXELS_TO_METERS;

		floor = createWall(0, 0, w, 1);
		ceil = createWall(0, h, w, 1);
		leftWall = createWall(0, 0, 1, h);
		rightWall = createWall(w, 0, 1, h);

		debugRender = new Box2DDebug();
	}

	World getWorld() {
		return world;
	}

	void update(float delta) {
		world.step(delta, 6, 2);

		Array<Body> bodies = new Array<>();
		world.getBodies(bodies);
		for (Body b : bodies) {
			BodyUserData data = (BodyUserData) b.getUserData();
			if (data != null) {
				if (data.isDeleteFlag()) {
					world.destroyBody(b);
				}
			}
		}
	}

	void debugDraw() {
		debugRender.render(world);
	}

	void setFloorCollisionHandler(Consumer<Body> handler) {
		world.setContactListener((CollisionHandler) contact -> {
			Body a = contact.getFixtureA().getBody();
			Body b = contact.getFixtureB().getBody();
			if (a == floor || b == floor) {
				if (a == floor && b != leftWall && b != rightWall) {
					handler.accept(b);
				} else if (b == floor && a != leftWall && a != rightWall) {
					handler.accept(a);
				}
			}
		});

	}

	private Body createWall(float x, float y, float hx, float hy) {
		BodyDef wallDef = new BodyDef();
		wallDef.type = BodyDef.BodyType.StaticBody;

		wallDef.position.set(x, y);
		FixtureDef fixtureDef3 = new FixtureDef();

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hx, hy);
		fixtureDef3.shape = shape;

		Body body = world.createBody(wallDef);
		body.createFixture(fixtureDef3);
		shape.dispose();
		return body;
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
