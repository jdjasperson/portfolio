package portfolio.application.boggle.dictionary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import portfolio.application.boggle.BoggleTestResources;
import portfolio.application.boggle.dictionary.Dictionary;

public class DictionaryTest {
	
	@Test
	public void testDictionary() {
		Dictionary dictionary = new Dictionary(null);
		try {
			Dictionary.Metrics metrics = dictionary.load(BoggleTestResources.DICTIONARY_FILE);
			System.out.println("Time to load Dictionary: " + metrics.getTimeToLoad() + " ms");
			System.out.println("Words loaded           : " + metrics.getWordsLoaded());
			System.out.println("Words skipped          : " + metrics.getWordsSkipped());
			Assertions.assertTrue(true);
		} catch(Exception e) {
			e.printStackTrace();
			Assertions.fail();
		}
	}
}
