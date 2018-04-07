package pl.lonski.wordtower;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

class PredefinedStageIterator implements StageIterator {

	private StageLoader loader;
	private int levelCount;
	private int lastLevel;
	private List<FileHandle> levels;

	@Override
	public void initialize(StageLoader loader) {
		this.loader = loader;
		this.levels = new LevelsList().getFileNames().stream()
				.map(fn -> "levels/" + fn)
				.map(path -> Gdx.files.internal(path))
				.sorted(Comparator.comparing(FileHandle::name))
				.collect(Collectors.toList());
		this.levelCount = this.levels.size();
		this.lastLevel = -1;
	}

	public boolean isLastLevel() {
		return (lastLevel + 1) == levelCount;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public PlayStage next() {
		lastLevel = (lastLevel + 1) % levelCount;
		return loader.load(levels.get(lastLevel));
	}

}
