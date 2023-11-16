package boggle.domain.dictionary;

import org.junit.Assert;
import org.junit.Test;

import boggle.domain.TestConstant;

public class DictionaryTest {

	@Test
	public void testDictionary() {
		Dictionary dictionary = new Dictionary(null);
		try {
			Dictionary.Metrics metrics = dictionary.load(TestConstant.DICTIONARY);
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
