package portfolio.application.boggle.dictionary;

public class MinimumWordLengthFilter implements Filter {
	private int minWordLength;
	
	public MinimumWordLengthFilter(int minWordLength) {
		this.minWordLength = minWordLength;
	}
	
	public boolean isValidWord(String word) {
		return word.length() >= this.minWordLength;
	}
}
