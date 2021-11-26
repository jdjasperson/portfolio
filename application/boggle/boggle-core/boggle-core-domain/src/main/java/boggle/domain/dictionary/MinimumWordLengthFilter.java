package boggle.domain.dictionary;

public class MinimumWordLengthFilter implements Filter {
	private int minWordLength;
	
	public MinimumWordLengthFilter(int minWordLength) {
		this.minWordLength = minWordLength;
	}
	
	@Override
	public boolean isValidWord(String word) {
		return word.length() >= this.minWordLength;
	}

}
