package pl.lonski.wordtower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DDebug {

	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;

	Box2DDebug() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false);
		debugRenderer = new Box2DDebugRenderer();
	}

	public void render(World world) {
		camera.update();
		debugRenderer.render(world, camera.combined);
	}

}
