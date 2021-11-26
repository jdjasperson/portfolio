package boggle.domain;

import java.util.ArrayList;

class BoggleNode {
	static final int N  = 0;
	static final int NE = 1;
	static final int E  = 2;
	static final int SE = 3;
	static final int S  = 4;
	static final int SW = 5;
	static final int W  = 6;
	static final int NW = 7;
	
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
		this.hasVisit = false;
	}
	
	void wire(BoggleNode[][] board, int[] moves) {
		for (int i = 0; i < moves.length; i++) {
			switch(moves[i]) {
				case N:
					this.transitions[N] = board[this.rowPos - 1][this.colPos];
					break;
				case NE:
					this.transitions[NE] = board[this.rowPos - 1][this.colPos + 1];
					break;
				case E:
					this.transitions[E] = board[this.rowPos][this.colPos + 1];
					break;
				case SE:
					this.transitions[SE] = board[this.rowPos + 1][this.colPos + 1];
					break;
				case S:
					this.transitions[S] = board[this.rowPos + 1][this.colPos];
					break;
				case SW:
					this.transitions[SW] = board[this.rowPos + 1][this.colPos - 1];
					break;
				case W:
					this.transitions[W] = board[this.rowPos][this.colPos - 1];
					break;
				case NW:
					this.transitions[NW] = board[this.rowPos - 1][this.colPos - 1];
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
	
	void visit() {
		this.hasVisit = true;
	}
	
	void unvisit() {
		this.hasVisit = false;
	}
	
	BoggleNode[] getTransitions() {
		return this.transitions;
	}
	
	public String toString() {
		return "{" + this.letter + ", " + this.rowPos + ", " + this.colPos + "}";
	}
}
