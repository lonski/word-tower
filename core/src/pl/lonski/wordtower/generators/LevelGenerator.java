package pl.lonski.wordtower.generators;

import static java.util.stream.Collectors.joining;
import static pl.lonski.wordtower.generators.GeneratorUtils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class LevelGenerator {

	private final WordSize wordSize;

	LevelGenerator(WordSize wordSize) {
		this.wordSize = wordSize;
	}

	String generateNuke() {
		Bounds bounds = new Bounds(COLS / 3, COLS / 2, ROWS / 4, ROWS / 3);

		List<String> top = generateTriangle(bounds, wordSize).stream()
				.filter(row -> countLetters(row) > 5)
				.collect(Collectors.toList());
		Collections.reverse(top);

		List<String> bottom = generateTriangle(bounds, wordSize).stream()
				.filter(row -> countLetters(row) > 5)
				.collect(Collectors.toList());

		List<String> nuke = generateTriangle(bounds, wordSize);
		nuke.addAll(top);
		nuke.addAll(bottom);

		return toLevelString(nuke);
	}

	String generateHourglass() {
		Bounds bounds = new Bounds(COLS / 3, COLS / 2, ROWS / 3, ROWS / 2);

		List<String> top = generateTriangle(bounds, wordSize).stream()
				.filter(row -> countLetters(row) > 5)
				.collect(Collectors.toList());
		Collections.reverse(top);

		List<String> bottom = generateTriangle(bounds, wordSize).stream()
				.filter(row -> countLetters(row) > 5)
				.collect(Collectors.toList());

		List<String> hourglass = new ArrayList<>();
		hourglass.addAll(top);
		hourglass.addAll(bottom);

		return toLevelString(hourglass);
	}

	private String toLevelString(List<String> levelShape) {
		return levelShape.stream()
				.map(row -> dots((COLS - row.length()) / 2) + row)
				.map(row -> row + dots((COLS - row.length()) / 2 + (COLS - row.length()) % 2))
				.collect(joining("\n"));
	}

}

