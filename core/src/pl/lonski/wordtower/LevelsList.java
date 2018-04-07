package pl.lonski.wordtower;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

public class LevelsList {

	private List<String> files;

	LevelsList() {
		this.files = Arrays.stream(Gdx.files.internal("levels-list.txt").readString().split("\n"))
				.collect(Collectors.toList());
	}

	List<String> getFileNames() {
		return files;
	}
}
