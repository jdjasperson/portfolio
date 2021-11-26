package boggle.domain.dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import boggle.domain.dictionary.trie.Trie;

public class Dictionary {
	private Filter[] filters;
	private Trie trie;
	
	// the transition matrix MUST be square, i.e. the # of cols == # of rows
	public Dictionary(Filter[] filters) {
		if (filters != null) {
			this.filters = filters;
		}
		else {
			this.filters = new Filter[0];
		}
		trie = new Trie();
	}
	
	public boolean hasWord(String word) {
		boolean retval = this.trie.hasWord(word);
		return retval;
	}
	
	/**
	 * The load(...) method will load a file, provided by the parameter, into the Dictionary
	 * data structure. Only files that have a single word per line will be properly parsed into
	 * the dictionary.
	 * 
	 * Only words that contain alphabet characters will be loaded into the Dictionary. All other
	 * words will be skipped.  This includes words that contain hyphens.
	 * 
	 * In the case where a transition matrix is provided to the constructor, any words that contain
	 * character sequences that do not have a valid transition will not be loaded into the Dictionary.
	 * 
	 * @param filename - the URI to the file to be loaded into the dictionary
	 * @return Dictionary.Metrics containing data points on the performance of the load process
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Dictionary.Metrics load(String filename) throws FileNotFoundException, IOException {
		long startTime = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String word = "";
		int wordsLoaded = 0;
		int wordsSkipped = 0;
		boolean isWordValid = false;
		while (word != null) {
			word = br.readLine();
			if (word != null) {
				word = word.toLowerCase();
				isWordValid = true;  // assume true in case no DictionaryFilters were supplied during construction

				for (int i = 0; i < this.filters.length; i++) {
					isWordValid = this.filters[i].isValidWord(word);
					if (!isWordValid) {
						break;
					}
				}
				
				if (isWordValid) {
					wordsLoaded++;
					trie.addWord(word);
				}
				else {
					wordsSkipped++;
				}
			}
		}
		
		try {
			if (br != null) {
				br.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return new Dictionary.Metrics(System.currentTimeMillis() - startTime, wordsLoaded, wordsSkipped);
	}
	
	public class Metrics {
		private long timeToLoad;
		private int wordsLoaded;
		private int wordsSkipped;
		
		public Metrics(long timeToLoad, int wordsLoaded, int wordsSkipped) {
			this.timeToLoad = timeToLoad;
			this.wordsLoaded = wordsLoaded;
			this.wordsSkipped = wordsSkipped;
		}
		
		public long getTimeToLoad() {
			return this.timeToLoad;
		}
		
		public int getWordsLoaded() {
			return this.wordsLoaded;
		}
		
		public int getWordsSkipped() {
			return this.wordsSkipped;
		}
	}
}
