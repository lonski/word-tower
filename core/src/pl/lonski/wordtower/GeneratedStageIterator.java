package pl.lonski.wordtower;

class GeneratedStageIterator implements StageIterator{

	private final LevelGenerator generator;
	private final StageLoader loader;

	GeneratedStageIterator(StageLoader loader) {
		this.generator = new LevelGenerator();
		this.loader = loader;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public PlayStage next() {
		String level = generator.generate();
		System.out.println(level);
		return loader.load(level);
	}
}
