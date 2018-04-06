package pl.lonski.wordtower;

import com.badlogic.gdx.files.FileHandle;

public class SinglePredefinedLevelIterator implements StageIterator {

	private final FileHandle file;
	private StageLoader loader;

	public SinglePredefinedLevelIterator(FileHandle file) {
		this.file = file;
	}

	@Override
	public void initialize(StageLoader loader) {
		this.loader = loader;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public PlayStage next() {
		return loader.load(file);
	}
}
