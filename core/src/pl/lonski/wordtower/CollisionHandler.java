package pl.lonski.wordtower;

import com.badlogic.gdx.physics.box2d.*;

public interface CollisionHandler extends ContactListener {

	void beginContact(Contact contact);

	default void endContact(Contact contact) {

	}

	default void preSolve(Contact contact, Manifold oldManifold) {

	}

	default void postSolve(Contact contact, ContactImpulse impulse) {

	}
}
