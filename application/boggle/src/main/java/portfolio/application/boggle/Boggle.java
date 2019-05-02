package portfolio.application.boggle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import portfolio.application.boggle.dictionary.Dictionary;
import portfolio.application.boggle.dictionary.Filter;
import portfolio.application.boggle.dictionary.MinimumWordLengthFilter;
import portfolio.application.boggle.dictionary.TransitionMatrixFilter;

public class Boggle {	
	public final static int MIN_WORD_LENGTH = 3;

	private final static ArrayList<String> EMPTY_WORD_LIST = new ArrayList<String>();
	private final static Filter[] DEFAULT_FILTERS = { new MinimumWordLengthFilter(MIN_WORD_LENGTH), new TransitionMatrixFilter() };
	
	private Board gameboard;
	private Dictionary dictionary;
	
	public Boggle(String dictionaryFilename, Filter[] filters) throws FileNotFoundException, IOException {
		this.gameboard = new Board();
		
		this.dictionary = new Dictionary(filters);
		this.dictionary.load(dictionaryFilename);
	}
	
	public Boggle(String boardFilename, String dictionaryFilename) throws FileNotFoundException, IOException {
		this.gameboard = new Board(boardFilename);

		((TransitionMatrixFilter)DEFAULT_FILTERS[1]).setTransitionMatrix(this.gameboard.getTransitionMatrix());
		
		this.dictionary = new Dictionary(DEFAULT_FILTERS);
		this.dictionary.load(dictionaryFilename);
	}
	
	public Metrics findAllWords() {
		ArrayList<String> wordsFound = new ArrayList<String>();
		BoggleNode[][] board = this.gameboard.getBoardMatrix();		
		int rows = this.gameboard.getRowCount();
		int columns = this.gameboard.getColumnCount();
		
		long startTime = System.currentTimeMillis();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				ArrayList<String> tmp = this.search(board[row][col], "");
				if (tmp.size() > 0) {
					wordsFound.addAll(tmp); // could alter this to capture the position of the starting letter, in order to present a list of words for each position in the board
				}
			}
		}
		
		long endTime = System.currentTimeMillis();

		Metrics retval = new Metrics(endTime - startTime, wordsFound);
		return retval;
	}
	
	private ArrayList<String> search(BoggleNode node, String word) {
		if (node.hasVisit())
			return EMPTY_WORD_LIST;
		
		node.setHasVisit(true);
		
		ArrayList<String> wordsFound = new ArrayList<String>();
		String newWord = word + node.getLetter();
		if (this.dictionary.hasWord(newWord)) {
			wordsFound.add(newWord);
		}
		
		BoggleNode[] tNodes = node.getTransitions();
		for (int i = 0; i < tNodes.length; i++) {
			if (!tNodes[i].hasVisit()) {
				ArrayList<String> tmp = this.search(tNodes[i], newWord);
				if (tmp.size() > 0) {
					wordsFound.addAll(tmp);
				}
			}
		}

		node.setHasVisit(false);
		
		return wordsFound;
	}
	
	public class Metrics {
		long timeToExecute;
		ArrayList<String> wordsFound;
		
		private Metrics(long timeToExecute, ArrayList<String> wordsFound) {
			this.timeToExecute = timeToExecute;
			this.wordsFound = wordsFound;
		}
		
		public long getTime() {
			return this.timeToExecute;
		}
		
		public ArrayList<String> getWordsFound() {
			return this.wordsFound;
		}
	}
}
