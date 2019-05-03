package portfolio.datastructure.trie;

import java.util.ArrayList;

public interface TrieNode {
	public void addChild(TrieNode child);
	public boolean hasChild(char edge);
	public TrieNode getChild(char edge);
	public String getPrefix();
	public boolean isWord(String toCompare);
	public char getEdge();
	public boolean isRoot();
	public boolean isLeaf();
	public ArrayList<TrieNode> getChildren();
}
