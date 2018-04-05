package pl.lonski.wordtower.generators;

import pl.lonski.wordtower.RandomUtils;

public class Bounds {

	final int width;
	final int height;

	Bounds(int minWidth, int maxWidth, int minHeight, int maxHeight) {
		width = RandomUtils.nextInt(minWidth, maxWidth + 1);
		height = RandomUtils.nextInt(minHeight, maxHeight + 1);
	}

}
