package portfolio.datastructure.trie;

import java.util.ArrayList;

public class TrieNodeV1 implements TrieNode {
	// ascii range A - Z == 65 .. 90, a - z == 97 .. 122
	// will require different offsets for indexing into the children array
	private final static int ALPHABET_LENGTH  = 27;
	private final static int LOWERCASE_INDEX1 = 97;
	private final static int LOWERCASE_INDEX2 = 122;
	private final static int LOWERCASE_OFFSET = 97;
	
	private TrieNode[] children = new TrieNodeV1[ALPHABET_LENGTH]; // total of all upper and lower case letters = 2 * 27
	private char edge; // the edge that allowed you to get to this TrieNode
	private String prefix;
	private boolean isWord;
	
	public TrieNodeV1(char edge, String prefix, boolean isWord) {
		this.edge = edge;
		this.prefix = prefix;
		this.isWord = isWord;
	}
	
	public void addChild(TrieNode child) {
		int index = this.getChildIndex(child.getEdge());
		if (this.children[index] != null) {
			throw new RuntimeException("Code error that is attempting to overwrite an existing TrieNode!");
		}
		this.children[index] = child;
	}
	
	private int getChildIndex(char edge) {
		int ascii = (int) edge;
		
		if (ascii <= TrieNodeV1.LOWERCASE_INDEX2 && ascii >= TrieNodeV1.LOWERCASE_INDEX1) {
			return ascii - TrieNodeV1.LOWERCASE_OFFSET;
		}
		
		throw new RuntimeException("The word contains an invalid or unsupported character (" + edge + ")");		
	}
	
	public boolean hasChild(char edge) {
		return this.getChild(edge) != null;
	}
	
	public TrieNode getChild(char edge) {
		int index = this.getChildIndex(edge);
		return this.children[index];
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public boolean isWord(String toCompare) {
		if (toCompare == null || !this.prefix.equals(toCompare)) {
			return this.isWord;
		}		
		else {
			this.isWord = true;
			return true;
		}
	}
	
	public char getEdge() {
		return this.edge;
	}
	
	public boolean isRoot() {
		return this.edge == '\0';
	}
	
	public boolean isLeaf() {
		for (int i = 0; i < this.children.length; i++) {
			if (this.children[i] != null) {
				return false;
			}
		}
		
		return true;
	}
	
	public ArrayList<TrieNode> getChildren() {
		ArrayList<TrieNode> retval = new ArrayList<TrieNode>();
		for (int i = 0; i < this.children.length; i++) {
			if (this.children[i] != null) {
				retval.add(this.children[i]);
			}
		}
		return retval;
	}
}
