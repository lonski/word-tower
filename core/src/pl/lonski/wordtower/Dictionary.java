package pl.lonski.wordtower;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.badlogic.gdx.files.FileHandle;

class Dictionary {

	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private final Map<Integer, List<String>> words;

	Dictionary(FileHandle file) {
		words = Stream.of(file.readString().split("\n"))
				.map(String::trim)
				.collect(groupingBy(String::length));
	}

	String getRandomWord(int length) {
		List<String> list = words.get(length);
		if (list == null) {
			return randomString(length);
		}
		String word = list.get(RandomUtils.nextInt(list.size()));
		return word.substring(0, 1).toUpperCase() + word.substring(1);
	}

	private String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(AB.charAt(RandomUtils.nextInt(AB.length())));
		}
		return sb.toString();
	}
}
