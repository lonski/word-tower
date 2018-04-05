package pl.lonski.wordtower;

import java.util.Random;

public class RandomUtils {

	private static final Random random = new Random();

	public static int nextInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}

	public static int nextInt(int bound) {
		return random.nextInt(bound);
	}

	public static boolean nextBoolean() {
		return random.nextBoolean();
	}
}
