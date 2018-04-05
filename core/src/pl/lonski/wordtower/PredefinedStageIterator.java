package pl.lonski.wordtower;

import com.badlogic.gdx.Gdx;

class PredefinedStageIterator implements StageIterator {

	private final StageLoader loader;
	private final int levelCount;
	private int lastLevel;

	PredefinedStageIterator(StageLoader loader, int levelCount) {
		this.loader = loader;
		this.levelCount = levelCount;
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
		String path = "levels/s" + (lastLevel + 1) + ".lev";
		return loader.load(Gdx.files.internal(path));
	}
}
