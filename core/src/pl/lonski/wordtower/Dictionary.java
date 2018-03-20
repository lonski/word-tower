package pl.lonski.wordtower;

import static java.util.stream.Collectors.groupingBy;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.badlogic.gdx.files.FileHandle;

public class Dictionary {

	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom rnd = new SecureRandom();

	private final Map<Integer, List<String>> words;

	public Dictionary(FileHandle file) {
		words = Stream.of(file.readString().split("\n"))
				.map(String::trim)
				.collect(groupingBy(String::length));
	}

	public String getRandomWord(int length) {
		List<String> list = words.get(length);
		if (list == null) {
			return randomString(length);
		}
		String word = list.get(rnd.nextInt(list.size()));
		return word.substring(0, 1).toUpperCase() + word.substring(1);
	}

	private String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		return sb.toString();
	}
}
