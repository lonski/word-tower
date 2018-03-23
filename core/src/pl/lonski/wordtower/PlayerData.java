package pl.lonski.wordtower;

public class PlayerData {

	private int score;
	private int wordsTyped;
	private int wordsMissed;

	public PlayerData addScore(int score) {
		this.score += score;
		return this;
	}

	public int getWordsTyped() {
		return wordsTyped;
	}

	public PlayerData incWordsTyped() {
		wordsTyped++;
		return this;
	}

	public int getWordsMissed() {
		return wordsMissed;
	}

	public PlayerData incWordsMissed() {
		wordsMissed++;
		return this;
	}

	public int getScore() {
		return score;
	}
}
