package portfolio.application.boggle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class Board {
	private final static int DEFAULT_SIZE     = 4;
	private final static int NORTHWEST_CORNER = 0;
	private final static int NORTHEAST_CORNER = 1;
	private final static int SOUTHWEST_CORNER = 2;
	private final static int SOUTHEAST_CORNER = 3;
	private final static int NORTH_EDGE       = 4;
	private final static int EAST_EDGE        = 5;
	private final static int SOUTH_EDGE       = 6;
	private final static int WEST_EDGE        = 7;
	private final static int MIDDLE           = 8;
	
	private final static int[] NORTHWEST_CORNER_TRANSITIONS = { BoggleNode.EAST, BoggleNode.SOUTH_EAST, BoggleNode.SOUTH };
	private final static int[] NORTHEAST_CORNER_TRANSITIONS = { BoggleNode.SOUTH, BoggleNode.SOUTH_WEST, BoggleNode.WEST };
	private final static int[] SOUTHWEST_CORNER_TRANSITIONS = { BoggleNode.NORTH, BoggleNode.NORTH_EAST, BoggleNode.EAST };
	private final static int[] SOUTHEAST_CORNER_TRANSITIONS = { BoggleNode.WEST, BoggleNode.NORTH_WEST, BoggleNode.NORTH };
	private final static int[] NORTH_EDGE_TRANSITIONS = { BoggleNode.EAST, BoggleNode.SOUTH_EAST, BoggleNode.SOUTH, BoggleNode.SOUTH_WEST, BoggleNode.WEST };
	private final static int[] EAST_EDGE_TRANSITIONS = { BoggleNode.NORTH, BoggleNode.SOUTH, BoggleNode.SOUTH_WEST, BoggleNode.WEST, BoggleNode.NORTH_WEST };
	private final static int[] SOUTH_EDGE_TRANSITIONS = { BoggleNode.NORTH, BoggleNode.NORTH_EAST, BoggleNode.EAST, BoggleNode.WEST, BoggleNode.NORTH_WEST };
	private final static int[] WEST_EDGE_TRANSITIONS = { BoggleNode.NORTH, BoggleNode.NORTH_EAST, BoggleNode.EAST, BoggleNode.SOUTH_EAST, BoggleNode.SOUTH };
	private final static int[] MIDDLE_TRANSITIONS = { BoggleNode.NORTH, BoggleNode.NORTH_EAST, BoggleNode.EAST, BoggleNode.SOUTH_EAST, BoggleNode.SOUTH, BoggleNode.SOUTH_WEST, BoggleNode.WEST, BoggleNode.NORTH_WEST };

	private final static int[][] TRANSITIONS = { 
		NORTHWEST_CORNER_TRANSITIONS, NORTHEAST_CORNER_TRANSITIONS,
		SOUTHWEST_CORNER_TRANSITIONS, SOUTHEAST_CORNER_TRANSITIONS,
		NORTH_EDGE_TRANSITIONS, EAST_EDGE_TRANSITIONS, SOUTH_EDGE_TRANSITIONS, WEST_EDGE_TRANSITIONS,
		MIDDLE_TRANSITIONS
	};

	private BoggleNode[][] board;
	private int rows;
	private int columns;
	private boolean[][] tMatrix = new boolean[26][26];
	
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
			this.generate(DEFAULT_SIZE);
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
			if (column == 0) return NORTHWEST_CORNER;
			if (column == colSize - 1) return NORTHEAST_CORNER;
			return NORTH_EDGE;
		}
		if (row == rowSize - 1) {
			if (column == 0) return SOUTHWEST_CORNER;
			if (column == colSize - 1) return SOUTHEAST_CORNER;
			return SOUTH_EDGE;
		}
		if (row <= rowSize - 2 && row >= 1) {
			if (column == 0) return WEST_EDGE;
			if (column == colSize - 1) return EAST_EDGE;
			return MIDDLE;
		}
		throw new RuntimeException("Coding error when attempting to determine state for {row=" + row + ", rowSize=" + rowSize + ", col=" + column + ", colSize=" + colSize);
	}
	
	private void generate(int boardSize) {
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
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>(27);
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
