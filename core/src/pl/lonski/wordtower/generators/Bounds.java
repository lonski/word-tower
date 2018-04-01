package pl.lonski.wordtower.generators;

import java.util.concurrent.ThreadLocalRandom;

public class Bounds {

	final int width;
	final int height;

	Bounds(int minWidth, int maxWidth, int minHeight, int maxHeight) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		width = random.nextInt(minWidth, maxWidth + 1);
		height = random.nextInt(minHeight, maxHeight + 1);
	}
}
