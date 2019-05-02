package portfolio.application.boggle.dictionary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import portfolio.application.boggle.dictionary.Dictionary;

public class DictionaryTest {
	private final static String RESOURCE_PATH = "/Users/jerryjasperson/devhome/source/personal/portfolio/application/boggle/src/main/resources";
	private final static String DICTIONARY_1 = "/usr/share/dict/web2";
	private final static String DICTIONARY_2 = DictionaryTest.RESOURCE_PATH + "/12dicts-5.0/2of12.txt";
	
	@Test
	public void testDictionary() {
		Dictionary dictionary = new Dictionary(null);
		try {
			Dictionary.Metrics metrics = dictionary.load(DICTIONARY_2);
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
