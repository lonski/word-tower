package pl.lonski.wordtower;

public class PlayerData {

	private int wordsTyped;
	private int wordsMissed;

	public int getWordsTyped() {
		return wordsTyped;
	}

	public PlayerData setWordsTyped(int wordsTyped) {
		this.wordsTyped = wordsTyped;
		return this;
	}

	public int getWordsMissed() {
		return wordsMissed;
	}

	public PlayerData setWordsMissed(int wordsMissed) {
		this.wordsMissed = wordsMissed;
		return this;
	}
}
