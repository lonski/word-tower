package pl.lonski.wordtower.actor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import pl.lonski.wordtower.KeyProcessor;

public class Word extends Group implements KeyProcessor {

	private final World world;
	private final Body body;
	private final Box box;
	private final List<Label> letters;
	private final Label.LabelStyle styleUntyped;
	private final Label.LabelStyle styleTyped;
	private final String text;

	private int characterIdx;

	public Word(World world, Skin skin, String text, float x, float y) {
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

	@Override
	public boolean keyTyped(char character) {
		if (characterIdx >= text.length()) {
			return false;
		}

		if (character == text.charAt(characterIdx)) {
			letters.get(characterIdx).setStyle(styleTyped);
			++characterIdx;
			if (characterIdx == text.length()) {
				setVisible(false); //TODO
				world.destroyBody(body);
			}
		}

		return false;
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

}
