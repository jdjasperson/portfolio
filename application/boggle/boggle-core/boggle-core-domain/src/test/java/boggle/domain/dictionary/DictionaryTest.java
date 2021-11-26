package boggle.domain.dictionary;

import org.junit.Assert;
import org.junit.Test;

public class DictionaryTest {
	public final static String DICTIONARY = "/Users/jerryjasperson/devhome/source/portfolio/application/boggle/resources/12dicts-5.0/2of12.txt";
	
	@Test
	public void testDictionary() {
		Dictionary dictionary = new Dictionary(null);
		try {
			Dictionary.Metrics metrics = dictionary.load(DICTIONARY);
			System.out.println("Time to load Dictionary: " + metrics.getTimeToLoad() + " ms");
			System.out.println("Words loaded           : " + metrics.getWordsLoaded());
			System.out.println("Words skipped          : " + metrics.getWordsSkipped());
			Assert.assertTrue(true);
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}