package pl.lonski.wordtower;

class GeneratedStageIterator implements StageIterator{

	private final NukeLevelGenerator generator;
	private final StageLoader loader;

	GeneratedStageIterator(StageLoader loader) {
		this.generator = new NukeLevelGenerator();
		this.loader = loader;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public PlayStage next() {
		String level = generator.generate();
		return loader.load(level);
	}
}
