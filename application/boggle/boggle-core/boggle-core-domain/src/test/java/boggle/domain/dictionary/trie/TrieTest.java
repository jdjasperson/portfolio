package boggle.domain.dictionary.trie;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

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
		Assert.assertTrue("Expected the number of words in the dictionary(" + dictionary.length + ") to match the number of words in the Trie(" + words.size() + ")",
			words.size() == dictionary.length);
		
		for (int i = 0; i < notDictionary.length; i++) {
			Assert.assertTrue("Expected to be unable to find the word \"" + notDictionary[i] +"\" but was able to!",
				!trie.hasWord(notDictionary[i]));
		}		
	}
}
