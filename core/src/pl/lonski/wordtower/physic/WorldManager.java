package pl.lonski.wordtower.physic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class WorldManager {

	private World world;
	private Box2DDebug debugRender;

	public WorldManager() {
		world = new World(new Vector2(0, -100), true);
		createFloor();
		debugRender = new Box2DDebug();
	}

	public World getWorld() {
		return world;
	}

	public void update(float delta) {
		world.step(delta, 6, 2);
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

	public void debugDraw() {
		debugRender.render(world);
	}
}
