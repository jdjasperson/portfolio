package boggle.domain.dictionary.trie;

import java.util.ArrayList;

public class Trie {
	private TrieNodeV2 root;
	
	public Trie() {
		this.root = new TrieNodeV2('\0', "", false);
	}
	
	/**
	 * Returns a Collection of all words in the Trie
	 * @return
	 */
	public ArrayList<String> getWords() {
		return this.getWords(this.root);
	}
	
	// used for recursive graph traversal, no depth limit enforced
	private ArrayList<String> getWords(TrieNodeV2 node) {
		ArrayList<String> retval = new ArrayList<String>();
		if (node.isWord(null)) {
			retval.add(node.getPrefix());
		}
		
		if (!node.isLeaf()) {
			ArrayList<TrieNodeV2> children = node.getChildren();
			for (int i = 0; i < children.size(); i++) {
				retval.addAll(this.getWords(children.get(i)));
			}
		}
		
		return retval;
	}
	
	/**
	 * Adds a word to the Trie.
	 * @param word
	 */
	public void addWord(String aWord) {
		String word = aWord.toLowerCase();
		TrieNodeV2 currentNode = this.getNode(word); 
		
		String prefix = currentNode.getPrefix();
		String tword = word.substring(prefix.length());

		if (currentNode.isWord(word) && tword.length() == 0) {
			return; // attempt to add a word already in the Trie
		}
		
		// we can now use the prefix contained by the TrieNode to construct
		// the rest of the graph for this word
		try {
			char[] ctword = tword.toCharArray();
			for (int i = 0; i < ctword.length; i++) {
				prefix = prefix + ctword[i];
				TrieNodeV2 trieNode = new TrieNodeV2(ctword[i], prefix, i == ctword.length - 1);
				currentNode.addChild(trieNode);
				currentNode = trieNode;
			}
		} catch(RuntimeException e) {
			System.out.println("Unable to add word: " + word);
			return;
		}
	}
	
	/**
	 * Searches the Trie for an occurance of 'word'.
	 * @param word
	 * @return true:boolean if the words exists in the Trie
	 */
	public boolean hasWord(String word) {
		char [] cword = word.toCharArray();
		TrieNodeV2 currentNode = root;
		for (int i = 0; i < cword.length; i++) {
			currentNode = currentNode.getChild(cword[i]);
			if (currentNode == null) {
				return false;
			}
			
			if (currentNode.isWord(null) && i == cword.length - 1) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Finds the TrieNode that corresponds to a given word. Such an operation is necessary
	 * in order to avoid the incorrect assumption that all new words added to the Trie
	 * must start at a leaf.  For example, if the word "assignment" is added before the 
	 * word "assign", this method will find the correct "n" node, for subsequent update
	 * to an isWord = true state.
	 * @param word
	 * @return
	 */
	private TrieNodeV2 getNode(String word) {
		char [] cword = word.toCharArray();
		
		TrieNodeV2 currentNode = this.root;
		for (int i = 0; i < cword.length; i++) {
			if (currentNode.hasChild(cword[i])) {
				currentNode = currentNode.getChild(cword[i]);
			}
			else {
				return currentNode;
			}
		}
		
		return currentNode;
	}
}
