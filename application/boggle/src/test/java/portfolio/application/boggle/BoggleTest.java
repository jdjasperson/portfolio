package portfolio.application.boggle;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import portfolio.application.boggle.Boggle;

public class BoggleTest {
	private final static String [] alphabet = {
		"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
		"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
	};
	
	@Test
	public void test() {
		try {
			Boggle boggle = new Boggle(BoggleTestResources.BOGGLE_BOARD, BoggleTestResources.DICTIONARY_FILE);
			Boggle.Metrics bMetrics = boggle.findAllWords();
			
			ArrayList<String> words = bMetrics.getWordsFound();
			System.out.println("Found a total of " + words.size() + " words in " + bMetrics.getTime() + " ms.");
			for (Iterator<String> i = words.iterator(); i.hasNext();) {
				System.out.println(i.next());
			}
		} catch(Exception e) {
			e.printStackTrace();
			Assertions.fail();
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
