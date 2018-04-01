package pl.lonski.wordtower.generators;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class GeneratorUtils {

	static final int ROWS = 16;
	static final int COLS = 76;

	static List<String> generateTriangle(Bounds bounds, WordSize size) {

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

	static String dots(int count) {
		return chars('.', count);
	}

	static String colons(int count) {
		return chars(':', count);
	}

	static String chars(char c, int count) {
		return Stream.generate(() -> c).limit(count).map(String::valueOf).collect(joining());
	}

	static int countLetters(String string) {
		return (int) string.chars().filter(c -> c == ':').count();
	}

}
