package boggle.domain.dictionary;

public class TransitionMatrixFilter implements Filter {
	private final static int ASCII_A = 97;
	private final static int ASCII_Z = 122;
	private boolean[][] tMatrix;
	
	public TransitionMatrixFilter() {
	}
	
	public TransitionMatrixFilter(boolean[][] transitionMatrix) {
		this.tMatrix = transitionMatrix;
	}
	
	public void setTransitionMatrix(boolean[][] matrix) {
		this.tMatrix = matrix;
	}
	
	@Override
	public boolean isValidWord(String word) {
		if (this.tMatrix == null)
			return true;
		
		char[] cWord = word.toCharArray();
		int fromLetter = 0;
		int toLetter = 0;
		for (int i = 0; i < cWord.length - 1; i++) {
			fromLetter = (int)cWord[i];
			toLetter = (int)cWord[i + 1];
			
			if (((fromLetter < ASCII_A || fromLetter > ASCII_Z) || (toLetter < ASCII_A || toLetter > ASCII_Z)) ||
				(this.tMatrix != null && !this.tMatrix[fromLetter - ASCII_A][toLetter - ASCII_A]))
			{
				return false;
			}
		}
		
		return true;
	}

}
