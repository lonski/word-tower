package pl.lonski.wordtower.generators;


import pl.lonski.wordtower.RandomUtils;

public class WordSize {

	final int min;
	final int max;

	WordSize(int minSize, int maxSize) {
		min = minSize;
		max = maxSize;
	}

	int randomSize() {
		return RandomUtils.nextInt(min, max);
	}

}
