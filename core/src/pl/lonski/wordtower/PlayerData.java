package pl.lonski.wordtower;

class PlayerData {

	private int score;
	private int wordsTyped;
	private int wordsMissed;

	void addScore(int score) {
		this.score += score;
	}

	int getWordsTyped() {
		return wordsTyped;
	}

	void incWordsTyped() {
		wordsTyped++;
	}

	int getWordsMissed() {
		return wordsMissed;
	}

	void incWordsMissed() {
		wordsMissed++;
	}

	int getScore() {
		return score;
	}
}
