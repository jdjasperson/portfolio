package portfolio.datastructure.trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class TrieNodeV2 implements TrieNode {
	// ascii range A - Z == 65 .. 90, a - z == 97 .. 122
	// will require different offsets for indexing into the children array
//	private final static int ALPHABET_LENGTH  = 27;
//	private final static int LOWERCASE_INDEX1 = 97;
//	private final static int LOWERCASE_INDEX2 = 122;
//	private final static int LOWERCASE_OFFSET = 97;
	
	//private TrieNode[] children = new TrieNode[ALPHABET_LENGTH]; // total of all upper and lower case letters = 2 * 27
	private HashMap<Character, TrieNode> children;
	private Character edge; // the edge that allowed you to get to this TrieNode
	private String prefix;
	private boolean isWord;
	
	public TrieNodeV2(char edge, String prefix, boolean isWord) {
		this.children = new HashMap<Character, TrieNode>();
		this.edge = Character.valueOf(edge);
		this.prefix = prefix;
		this.isWord = isWord;
	}
	
	public void addChild(TrieNode child) {
		if (this.children.containsKey(child.getEdge())) {
			throw new RuntimeException("Code error that is attempting to overwrite an existing TrieNode!");
		}
		
		this.children.put(child.getEdge(), child);
	}

	public boolean hasChild(char edge) {
		return this.children.containsKey(edge);
	}
	
	public TrieNode getChild(char edge) {
		return this.children.get(edge);
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
		return this.edge.charValue() == '\0';
	}
	
	public boolean isLeaf() {
		return this.children.isEmpty();
	}
	
	public ArrayList<TrieNode> getChildren() {
		ArrayList<TrieNode> retval = new ArrayList<TrieNode>();
		Collection<TrieNode> nodes = this.children.values();
		Iterator<TrieNode> iter = nodes.iterator();
		while (iter.hasNext()) {
			retval.add(iter.next());
		}
		return retval;
	}
}
