package portfolio.application.boggle;

import java.util.ArrayList;

class BoggleNode {
	static final int NORTH      = 0;
	static final int NORTH_EAST = 1;
	static final int EAST       = 2;
	static final int SOUTH_EAST = 3;
	static final int SOUTH      = 4;
	static final int SOUTH_WEST = 5;
	static final int WEST       = 6;
	static final int NORTH_WEST = 7;
	
	private String letter;
	private int rowPos;
	private int colPos;
	private BoggleNode[] transitions;
	private boolean hasVisit;
	
	BoggleNode(String letter, int rowPos, int colPos) {
		this.letter = letter;
		this.rowPos = rowPos;
		this.colPos = colPos;
		this.transitions = new BoggleNode[8];
	}
	
	void wire(BoggleNode[][] board, int[] moves) {
		for (int i = 0; i < moves.length; i++) {
			switch(moves[i]) {
				case NORTH:
					this.transitions[NORTH] = board[this.rowPos - 1][this.colPos];
					break;
				case NORTH_EAST:
					this.transitions[NORTH_EAST] = board[this.rowPos - 1][this.colPos + 1];
					break;
				case EAST:
					this.transitions[EAST] = board[this.rowPos][this.colPos + 1];
					break;
				case SOUTH_EAST:
					this.transitions[SOUTH_EAST] = board[this.rowPos + 1][this.colPos + 1];
					break;
				case SOUTH:
					this.transitions[SOUTH] = board[this.rowPos + 1][this.colPos];
					break;
				case SOUTH_WEST:
					this.transitions[SOUTH_WEST] = board[this.rowPos + 1][this.colPos - 1];
					break;
				case WEST:
					this.transitions[WEST] = board[this.rowPos][this.colPos - 1];
					break;
				case NORTH_WEST:
					this.transitions[NORTH_WEST] = board[this.rowPos - 1][this.colPos - 1];
					break;
				default:
					throw new RuntimeException("Hava an invalid transition of " + moves[i]);
			}
		}
		
		ArrayList<BoggleNode> tmp = new ArrayList<BoggleNode>();
		for (int i = 0; i < this.transitions.length; i++) {
			if (transitions[i] != null) {
				tmp.add(transitions[i]);
			}
		}
		
		this.transitions = tmp.toArray(new BoggleNode[tmp.size()]);
	}
		
	String getLetter() {
		return this.letter;
	}
	
	boolean hasVisit() {
		return this.hasVisit;
	}
	
	void setHasVisit(boolean value) {
		this.hasVisit = value;
	}
	
	BoggleNode[] getTransitions() {
		return this.transitions;
	}
	
	public String toString() {
		return "{" + this.letter + ", " + this.rowPos + ", " + this.colPos + "}";
	}
}
