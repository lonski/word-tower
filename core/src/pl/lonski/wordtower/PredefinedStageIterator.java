package pl.lonski.wordtower;

import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

class PredefinedStageIterator implements StageIterator {

	private StageLoader loader;
	private int levelCount;
	private int lastLevel;
	private FileHandle[] levels;

	@Override
	public void initialize(StageLoader loader) {
		this.loader = loader;
		this.levels = Gdx.files.internal("levels").list();
		Arrays.sort(this.levels, Comparator.comparing(FileHandle::name));
		this.levelCount = this.levels.length;
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
		return loader.load(levels[lastLevel]);
	}

}
