package pl.lonski.wordtower;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

class Word extends Group {

	private final World world;
	private final Body body;
	private final Box box;
	private final List<Label> letters;
	private final Label.LabelStyle styleUntyped;
	private final Label.LabelStyle styleTyped;
	private final String text;

	private int characterIdx;

	Word(World world, Skin skin, String text, float x, float y) {
		this.world = world;
		this.letters = new ArrayList<>();
		this.text = text;
		this.styleTyped = new Label.LabelStyle(skin.getFont("currier-font"), Color.RED);
		this.styleUntyped = new Label.LabelStyle(skin.getFont("currier-font"), Color.GREEN);

		float textWidth = 10;
		float textHeight = 0;
		for (char c : text.toCharArray()) {
			Label letter = new Label(String.valueOf(c), styleUntyped);
			letter.setPosition(textWidth, 10);
			textWidth += letter.getWidth();
			textHeight = Math.max(textHeight, letter.getHeight());
			this.addActor(letter);
			this.letters.add(letter);
		}

		setPosition(x, y);
		setBounds(getX(), getY(), textWidth + 10, textHeight + 15);

		this.box = new Box((int) getWidth(), (int) getHeight(), Color.CYAN);
		this.box.setPosition(0, 0);
		this.addActor(box);
		this.body = createBody(world);
	}

	@Override
	public void act(float delta) {
		setPosition(body.getPosition().x, body.getPosition().y);
		setRotation((float) Math.toDegrees(body.getAngle()));
	}

	void eatCharacter(char character) {
		if (!isCompleted() && character == text.charAt(characterIdx)) {
			letters.get(characterIdx).setStyle(styleTyped);
			++characterIdx;
		}
	}

	boolean isCompleted() {
		return characterIdx == text.length();
	}

	void reset() {
		letters.forEach(w -> w.setStyle(styleUntyped));
		characterIdx = 0;
	}

	private Body createBody(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(getX(), getY());

		Body body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(
				getWidth() / 2, getHeight() / 2,
				new Vector2(getWidth() / 2, getHeight() / 2),
				0
		);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		body.createFixture(fixtureDef);

		shape.dispose();

		return body;
	}

	@Override
	public boolean remove() {
		world.destroyBody(body);
		return super.remove();
	}

	static class Box extends Actor {

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

}
