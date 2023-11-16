package boggle.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;


public class BoggleTest {
	private final static String [] alphabet = {
		"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
		"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
	};
	
	@Test
	public void test() {
		try {
			Boggle boggle = new Boggle(TestConstant.BOGGLE_BOARD, TestConstant.DICTIONARY);
			//this.printTMatrix(boggle.getBoard().getTransitionMatrix());
			//this.printBoard(boggle.getBoard().getBoardMatrix());
			Boggle.Metrics bMetrics = boggle.findAllWords();
			
			ArrayList<String> words = bMetrics.getWordsFound();
			System.out.println("Found a total of " + words.size() + " words in " + bMetrics.getTime() + " ms.");
			
			//this.printWords(words);
			
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void printWords(Collection<String> words) {
		for (Iterator<String> i = words.iterator(); i.hasNext();) {
			System.out.println(i.next());
		}
	}
	
	private void printBoard(BoggleNode[] [] board) {
		System.out.println("---------- Game Board ----------");
		for (int i = 0; i < board.length; i++) {
			StringBuffer sb = new StringBuffer(board[0].length);
			for (int j = 0; j < board[i].length; j++) {
				sb.append(board[i][j].getLetter()).append(" ");
			}
			System.out.println(sb.toString());
		}
		System.out.println();
	}
	
	private void printTMatrix(boolean[][] tMatrix) {
		System.out.println("---------- Transition Matrix ----------");
		System.out.print("  ");
		for (int i = 0; i < alphabet.length; i++) {
			System.out.print(alphabet[i] + " ");
		}
		
		for (int row = 0; row < tMatrix.length; row++) {
			System.out.print("\n" + alphabet[row] + " ");

			for (int col = 0; col < tMatrix[row].length; col++) {
				if (tMatrix[row][col]) {
					System.out.print("*");
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
