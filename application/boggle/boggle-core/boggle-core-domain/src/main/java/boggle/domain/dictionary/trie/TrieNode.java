package boggle.domain.dictionary.trie;

import java.util.ArrayList;

public interface TrieNode {
	public void addChild(TrieNode childNode);
	public boolean hasChild(char edge);
	public TrieNode getChild(char edge);
	public String getPrefix();
	public boolean isWord(String toCompare);
	public char getEdge();
	public boolean isRoot();
	public boolean isLeaf();
	public ArrayList<TrieNode> getChildren();
}
