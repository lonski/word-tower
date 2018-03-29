package pl.lonski.wordtower;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class NukeLevelGenerator {

	static final int ROWS = 16;
	static final int COLS = 76;

	private final ThreadLocalRandom random;

	NukeLevelGenerator() {
		random = ThreadLocalRandom.current();
	}

	public static void main(String[] args) {
		NukeLevelGenerator generator = new NukeLevelGenerator();
		System.out.println(generator.generate());
	}

	String generate() {
		Bounds bounds = new Bounds(COLS / 3, COLS / 2, ROWS / 4, ROWS / 3);
		WordSize size = new WordSize(2, 10);
		List<String> hourglassTop = generateTriangle(bounds, size).stream()
				.filter(row -> countLetters(row) > 5)
				.collect(Collectors.toList());
		Collections.reverse(hourglassTop);

		List<String> hourglassBottom = generateTriangle(bounds, size).stream()
				.filter(row -> countLetters(row) > 5)
				.collect(Collectors.toList());


		List<String> hourglass = generateTriangle(bounds, size);
		hourglass.addAll(hourglassTop);
		hourglass.addAll(hourglassBottom);

		return hourglass.stream()
				.map(row -> dots((COLS - row.length()) / 2) + row)
				.map(row -> row + dots((COLS - row.length()) / 2 + (COLS - row.length()) % 2))
				.collect(joining("\n"));
	}

	private List<String> generateTriangle(Bounds bounds, WordSize size) {
		List<String> triangle = new ArrayList<>();
		int reductionPerFloor = ((bounds.width - size.min - 2) / bounds.height);
		for (int floorIdx = bounds.height; floorIdx > 0; floorIdx--) {
			int reduction = (reductionPerFloor * floorIdx);
			StringBuilder row = new StringBuilder(dots(reduction / 2));
			while (row.length() + reduction / 2 < bounds.width) {
				int randomizedSize = size.randomSize();
				int remainingRowSize = bounds.width - row.length() - reduction / 2;
				int wordSize = Math.min(remainingRowSize, randomizedSize) + 2;
				if ((remainingRowSize - wordSize) <= (2 + size.min)) {
					wordSize = Math.max(remainingRowSize, size.min + 2);
				}
				row.append("[").append(colons(wordSize - 2)).append("]");
			}
			triangle.add(row + dots(reduction / 2));
		}
		return triangle;
	}

	private String dots(int count) {
		return chars('.', count);
	}

	private String colons(int count) {
		return chars(':', count);
	}

	private String chars(char c, int count) {
		return Stream.generate(() -> c).limit(count).map(String::valueOf).collect(joining());
	}

	private int countLetters(String string) {
		return (int) string.chars().filter(c -> c == ':').count();
	}

	private class Bounds {

		final int width;
		final int height;

		Bounds(int minWidth, int maxWidth, int minHeight, int maxHeight) {
			width = random.nextInt(minWidth, maxWidth + 1);
			height = random.nextInt(minHeight, maxHeight + 1);
		}
	}

	private class WordSize {

		final int min;
		final int max;

		WordSize(int minSize, int maxSize) {
			min = minSize;
			max = maxSize;
		}

		int randomSize() {
			return random.nextInt(min, max);
		}

	}
}
