package boggle.domain;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import boggle.domain.Boggle;

public class BoggleTest {
	private final static String [] alphabet = {
		"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
		"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
	};
	
	private final static String BOGGLE_BOARD = "/Users/jerryjasperson/Desktop/Boggle/4x4.txt";
	private final static String DICTIONARY_FILE   = "/Users/jerryjasperson/Desktop/Boggle/12dicts-5.0/2of12.txt";

	@Test
	public void test() {
		try {
			Boggle boggle = new Boggle(BOGGLE_BOARD, DICTIONARY_FILE);
			Boggle.Metrics bMetrics = boggle.findAllWords();
			
			ArrayList<String> words = bMetrics.getWordsFound();
			System.out.println("Found a total of " + words.size() + " words in " + bMetrics.getTime() + " ms.");
//			for (Iterator<String> i = words.iterator(); i.hasNext();) {
//				System.out.println(i.next());
//			}
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void printTMatrix(boolean[][] tMatrix) {
		System.out.print("  ");
		for (int i = 0; i < alphabet.length; i++) {
			System.out.print(alphabet[i] + " ");
		}
		for (int row = 0; row < tMatrix.length; row++) {
			System.out.print("\n" + alphabet[row] + " ");

			for (int col = 0; col < tMatrix[row].length; col++) {
				if (tMatrix[row][col]) {
					System.out.print("T");
				}
				else {
					System.out.print(" ");
				}
				System.out.print(" ");
			}
		}
		System.out.println();
	}
}
