package pl.lonski.wordtower;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

class LevelGenerator {

	static final int ROWS = 16;
	static final int COLS = 76;

	private final ThreadLocalRandom random;

	LevelGenerator() {
		random = ThreadLocalRandom.current();
	}

	public static void main(String[] args) {
		LevelGenerator generator = new LevelGenerator();
		System.out.println(generator.generate());
	}

	String generate() {
		Bounds bounds = new Bounds(15, 40, 4, 8);
		WordSize size = new WordSize(2, 10);
		List<String> triangle = generateTriangle(bounds, size);
		System.out.println(triangle.stream().collect(joining("\n")));
		return triangle.stream()
				.map(row -> dots((COLS - row.length()) / 2) + row)
				.map(row -> row + dots((COLS - row.length()) / 2 + (COLS - row.length()) % 2))
				.collect(joining("\n"));
	}

	private List<String> generateTriangle(Bounds bounds, WordSize size) {
		List<String> triangle = new ArrayList<>();

		int reductionPerFloor = (bounds.width - size.min) / (bounds.height - 1);

		for (int floorIdx = 0; floorIdx < bounds.height; floorIdx++) {
			int reduction = (reductionPerFloor * floorIdx);
			String row = dots(reduction / 2);
			while (row.length() + reduction / 2 != bounds.width) {
				int randomizedSize = size.randomSize();
				int remainingRowSize = bounds.width - row.length() - reduction / 2;
				int wordSize = Math.min(remainingRowSize, randomizedSize);
				if (remainingRowSize - wordSize <= 4) {
					wordSize = remainingRowSize - 2;
				}
				row += "[" + dots(wordSize) + "]";
			}
			triangle.add(row + dots(reduction / 2));
		}

		for (int floorIdx = bounds.height; floorIdx > 0; floorIdx--) {
			int reduction = (reductionPerFloor * floorIdx);
			String row = dots(reduction / 2);
			while (row.length() + reduction / 2 != bounds.width) {
				int randomizedSize = size.randomSize();
				int remainingRowSize = bounds.width - row.length() - reduction / 2;
				int wordSize = Math.min(remainingRowSize, randomizedSize);
				if (remainingRowSize - wordSize <= 4) {
					wordSize = remainingRowSize - 2;
				}
				row += "[" + dots(wordSize) + "]";
			}
			triangle.add(row + dots(reduction / 2));
		}

		return triangle;
	}

	private List<String> generateRectangle(Bounds bounds, WordSize size) {
		List<String> rectangle = new ArrayList<>();

		for (int floorIdx = 0; floorIdx < bounds.height; floorIdx++) {
			String row = "";
			while (row.length() != bounds.width) {
				int randomizedSize = size.randomSize();
				int remainingRowSize = bounds.width - row.length();
				int wordSize = Math.min(remainingRowSize, randomizedSize);
				if (remainingRowSize - wordSize <= 4) {
					wordSize = remainingRowSize - 2;
				}
				row += "[" + dots(wordSize) + "]";
			}
			rectangle.add(row);
		}

		return rectangle;
	}

	private String dots(int count) {
		return Stream.generate(() -> ".").limit(count).collect(joining());
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
