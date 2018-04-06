package pl.lonski.wordtower;

import pl.lonski.wordtower.generators.GeneratedStageIterator;

public class MixedStageIterator implements StageIterator {

	private PredefinedStageIterator predefinedStageIterator;
	private GeneratedStageIterator generatedStageIterator;

	@Override
	public void initialize(StageLoader loader) {
		this.predefinedStageIterator = new PredefinedStageIterator();
		this.predefinedStageIterator.initialize(loader);
		this.generatedStageIterator = new GeneratedStageIterator();
		this.generatedStageIterator.initialize(loader);
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public PlayStage next() {
		if (predefinedStageIterator.isLastLevel()) {
			return generatedStageIterator.next();
		}
		return predefinedStageIterator.next();
	}

}
