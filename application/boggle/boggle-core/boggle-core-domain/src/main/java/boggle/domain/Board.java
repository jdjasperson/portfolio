package boggle.domain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class Board {
	private final static int DEFAULT_SIZE = 4;
	private final static int NW_CORNER    = 0;
	private final static int NE_CORNER    = 1;
	private final static int SW_CORNER    = 2;
	private final static int SE_CORNER    = 3;
	private final static int N_EDGE       = 4;
	private final static int E_EDGE       = 5;
	private final static int S_EDGE       = 6;
	private final static int W_EDGE       = 7;
	private final static int INTERIOR     = 8;
	
	// these are the conditions of the board; a node is either in a corner, on the edge of the board
	// or in the interior of the board.
	// a corner node has 3 adjacent nodes
	// an edge node has 5 adjacent nodes
	// an interior node has 8 adjacent nodes
	private final static int[] NW_CORNER_TRANSITIONS = { BoggleNode.E, BoggleNode.SE, BoggleNode.S };
	private final static int[] NE_CORNER_TRANSITIONS = { BoggleNode.S, BoggleNode.SW, BoggleNode.W };
	private final static int[] SW_CORNER_TRANSITIONS = { BoggleNode.N, BoggleNode.NE, BoggleNode.E };
	private final static int[] SE_CORNER_TRANSITIONS = { BoggleNode.W, BoggleNode.NW, BoggleNode.N };
	private final static int[] N_EDGE_TRANSITIONS = { BoggleNode.E, BoggleNode.SE, BoggleNode.S, BoggleNode.SW, BoggleNode.W };
	private final static int[] E_EDGE_TRANSITIONS = { BoggleNode.N, BoggleNode.S, BoggleNode.SW, BoggleNode.W, BoggleNode.NW };
	private final static int[] S_EDGE_TRANSITIONS = { BoggleNode.N, BoggleNode.NE, BoggleNode.E, BoggleNode.W, BoggleNode.NW };
	private final static int[] W_EDGE_TRANSITIONS = { BoggleNode.N, BoggleNode.NE, BoggleNode.E, BoggleNode.SE, BoggleNode.S };
	private final static int[] INTERIOR_TRANSITIONS = { BoggleNode.N, BoggleNode.NE, BoggleNode.E, BoggleNode.SE, BoggleNode.S, BoggleNode.SW, BoggleNode.W, BoggleNode.NW };

	private final static int[][] TRANSITIONS = { 
		NW_CORNER_TRANSITIONS, NE_CORNER_TRANSITIONS, SW_CORNER_TRANSITIONS, SE_CORNER_TRANSITIONS,
		N_EDGE_TRANSITIONS, E_EDGE_TRANSITIONS, S_EDGE_TRANSITIONS, W_EDGE_TRANSITIONS,
		INTERIOR_TRANSITIONS
	};

	// if we converted the 2-dimension array into a trie data structure, we'd likely
	// see a significant performance improvement!
	private BoggleNode[][] board;
	private int rows;
	private int columns;
	private boolean[][] tMatrix = new boolean[26][26]; // 
	
	Board() {
		this(DEFAULT_SIZE);
	}
	
	Board(int boardSize) {
		this.board = new BoggleNode[boardSize][boardSize];
		this.generate(boardSize);
		this.loadTransitionMatrix();		
	}
	
	Board(String filename) {
		try {
			this.loadBoard(filename);
		} catch(Exception e) {
			this.generate(Board.DEFAULT_SIZE);
		}
		
		this.loadTransitionMatrix();
	}
	
	public boolean[][] getTransitionMatrix() {
		return this.tMatrix;
	}
	
	public int getRowCount() {
		return this.rows;
	}
	
	public int getColumnCount() {
		return this.columns;
	}
	
	public BoggleNode[][] getBoardMatrix() {
		return this.board;
	}
	
	// after loading the board, we could gain further performance improvements by
	// populating a trie structure with the board data, as well! The only caveat is
	// that we need to either A) limit the word length or B) detect prior trie node
	// visits in order to avoid an infinite loop. For board sizes where N >= 6, option
	// B can still result in very large data structures!
	private void loadBoard(String filename) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = "";
		ArrayList<BoggleNode[]> rows = new ArrayList<BoggleNode[]>();
		int row = 0;
		while (line != null) {
			line = br.readLine();
			if (line != null) {
				String[] columns = line.toLowerCase().split(" ");
				
				BoggleNode[] nodes = new BoggleNode[columns.length];
				for (int j = 0; j < columns.length; j++) {
					nodes[j] = new BoggleNode(columns[j], row, j);
				}
				rows.add(nodes);
				row++;
			}
		}
		
		if (br != null) {
			br.close();
		}
		
		// now convert to the 2-dim array
		this.rows = rows.size();
		this.columns = rows.get(0).length;
		this.board = new BoggleNode[this.rows] [this.columns];
		
		for (int i = 0; i < this.rows; i++) {
			this.board[i] = rows.get(i);
		}
		
		// wire up transitions
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				int state = this.getState(i, this.board.length, j, this.board[i].length);
				this.board[i][j].wire(this.board, TRANSITIONS[state]);
			}
		}		
	}
	
	private int getState(int row, int rowSize, int column, int colSize) {
		if (row == 0) {
			if (column == 0) return NW_CORNER;
			if (column == colSize - 1) return NE_CORNER;
			return N_EDGE;
		}
		if (row == rowSize - 1) {
			if (column == 0) return SW_CORNER;
			if (column == colSize - 1) return SE_CORNER;
			return S_EDGE;
		}
		if (row <= rowSize - 2 && row >= 1) {
			if (column == 0) return W_EDGE;
			if (column == colSize - 1) return E_EDGE;
			return INTERIOR;
		}
		throw new RuntimeException("Coding error when attempting to determine state for {row=" + row + ", rowSize=" + rowSize + ", col=" + column + ", colSize=" + colSize);
	}
	
	private void generate(int boardSize) {
		// given a board of size N x N, randomly populate the board with characters
	}
	
	/**
	 * Construct an array like
	 *   a b c d e f ...
	 * a f t t f f f
	 * b
	 * c
	 * d
	 * e
	 * f
	 * @return
	 */
	private void loadTransitionMatrix() {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>(26);
		BoggleNode currentNode;
		// construct a list of all transitions that each letter can have
		// in the current board
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				currentNode = this.board[i][j];
				if (!map.containsKey(currentNode.getLetter())) {
					map.put(this.board[i][j].getLetter(), new ArrayList<String>());
				}
				
				ArrayList<String> validLetterTransitions = map.get(this.board[i][j].getLetter());
				BoggleNode[] nodeTransitions = currentNode.getTransitions();
				for (int k = 0; k < nodeTransitions.length; k++) {
					String neighboringLetter = nodeTransitions[k].getLetter();
					if (!validLetterTransitions.contains(neighboringLetter)) {
						validLetterTransitions.add(neighboringLetter);
					}
				}
			}
		}
		
		// now let's construct the matrix; we'll work on a - z by rows and columns
		Iterator<String> keyIter = map.keySet().iterator();
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			char[] ckey = key.toCharArray();
			int rowIndex = ((int)ckey[0]) - 97;
			ArrayList<String> colStrings = map.get(key);
			for (int i = 0; i < colStrings.size(); i++) {
				char[] col = colStrings.get(i).toCharArray();
				int colIndex = ((int)col[0]) - 97;
				this.tMatrix[rowIndex][colIndex] = true;
			}
		}
	}
}
