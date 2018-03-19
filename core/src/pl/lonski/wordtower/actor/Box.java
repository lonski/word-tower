package pl.lonski.wordtower.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Box extends Actor {

	private TextureRegion texture;

	Box(int width, int height, Color color) {
		texture = new TextureRegion(createTexture(width, height, color));
		setBounds(0, 0, width, height);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(
				texture,
				getX(), getY(),
				getOriginX(), getOriginY(),
				getWidth(), getHeight(),
				getScaleX(),
				getScaleY(),
				getRotation()
		);
	}

	private Texture createTexture(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.drawRectangle(0, 0, width, height);
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}
}

