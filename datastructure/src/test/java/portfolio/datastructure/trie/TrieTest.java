package portfolio.datastructure.trie;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrieTest {
	private static String[] dictionary = {
		"asset", "ass", "ascot", "ad", "administrator"
	};
	
	private static String[] notDictionary = {
		"assylum", "zipper"
	};

	@Test
	public void testTrie() {
		Trie trie = new Trie();
		for (int i = 0; i < dictionary.length; i++) {
			trie.addWord(dictionary[i]);
		}
		
		ArrayList<String> words = trie.getWords();
		Assertions.assertTrue(words.size() == dictionary.length,
			"Expected the number of words in the dictionary(" + dictionary.length + ") to match the number of words in the Trie(" + words.size() + ")");
		
		for (int i = 0; i < notDictionary.length; i++) {
			Assertions.assertTrue(!trie.hasWord(notDictionary[i]),
				"Expected to be unable to find the word \"" + notDictionary[i] +"\" but was able to!");
		}		
	}
}
