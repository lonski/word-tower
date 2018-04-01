package pl.lonski.wordtower.generators;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import pl.lonski.wordtower.PlayStage;
import pl.lonski.wordtower.StageIterator;
import pl.lonski.wordtower.StageLoader;

public class GeneratedStageIterator implements StageIterator {

	private final StageLoader loader;

	private List<Supplier<String>> levels;
	private int lastLevel;

	public GeneratedStageIterator(StageLoader loader) {
		LevelGenerator generator = new LevelGenerator(new WordSize(2, 10));
		this.loader = loader;
		this.lastLevel = -1;
		this.levels = Arrays.asList(
				generator::generateHourglass,
				generator::generateNuke
		);
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public PlayStage next() {
		lastLevel = (lastLevel + 1) % levels.size();
		String level = levels.get(lastLevel).get();
		return loader.load(level);
	}
}
