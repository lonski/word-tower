package pl.lonski.wordtower.generators;

import java.util.concurrent.ThreadLocalRandom;

public class WordSize {

	final int min;
	final int max;

	WordSize(int minSize, int maxSize) {
		min = minSize;
		max = maxSize;
	}

	int randomSize() {
		return ThreadLocalRandom.current().nextInt(min, max);
	}

}
