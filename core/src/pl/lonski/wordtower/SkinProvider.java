package pl.lonski.wordtower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

class SkinProvider {

	private static Skin skin;

	static Skin getSkin() {
		if (skin == null) {
			skin = new Skin(Gdx.files.internal("skins/visui/uiskin.json"));
			skin.add(
					"currier-font",
					new BitmapFont(Gdx.files.internal("fonts/currier.fnt")),
					BitmapFont.class
			);
		}
		return skin;
	}
}
