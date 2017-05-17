package crossword;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
	/*
	 * occupation map contains all letters that were inserted into board. It
	 * allows to create constraints for new words.
	 */
	private char[][] board;
	public ArrayList<char[][]> solutions = new ArrayList<char[][]>();
	public ArrayList<String> usedWords = new ArrayList<String>();
	public int wordsMatched = 0;
	private int row;
	private int column;
	// private String currentWord = "";

	public Board() {
		this.board = new char[Constants.HEIGHT][Constants.WIDTH];
		for (int r = 0; r < Constants.HEIGHT; r++) {
			for (int c = 0; c < Constants.WIDTH; c++)
				this.board[r][c] = Constants.DEFAULT_EMPTY_CELL;
		}
		this.insertFirstWord();
		this.solutions.add(this.board);
	}

	public void insertFirstWord() {
		String keyword = "";
		// for (String w : WordsList.wordsList) {
		// if (w.length() <= Constants.WIDTH) {
		// keyword = w;
		// break;
		// }
		// }
		// while (keyword.length() > Constants.WIDTH) {
		int random = ThreadLocalRandom.current().nextInt(0, WordsList.wordsList.size());
		keyword = WordsList.wordsList.get(random);
		// }
		int row = 0;
		// this.row = row;

		for (int i = 0; i < keyword.length(); i++) {
			this.board[row][i] = keyword.charAt(i);
		}

		this.usedWords.add(keyword);
	}

	public boolean bactracking() {
		int[] variable = findPosition();
		if ((variable[0] == -1) || this.solutions.size() >= Constants.NUMBER_OF_WORDS_TO_INSERT) {
			System.out.println("success");
			return true;
		}

		int row = variable[0];
		int column = variable[1];
		int direction = variable[2];

		for (String w : WordsList.wordsList) {
			if (usedWords.contains(w)) {
				continue;
			}
			boolean success = false;

			if (direction == Constants.HORIZONTAL) {
				if (wordFitsRow(w, row)) {
					insertIntoRow(w, row);
					success = true;
				}
			} else {
				// vertical
				if (wordFitsColumn(w, column)) {
					insertIntoColumn(w, column);
					success = true;
				}
			}

			if (success) {
				solutions.add(board.clone());
				System.out.println(w);
				this.displayBoard();
				usedWords.add(w);
				if (bactracking()) {
					return true;
				} else {
					this.solutions.remove(this.solutions.size() - 1);
					this.board = this.solutions.get(0);
					usedWords.remove(w);
				}

			}
		}

		return false;
	}

	public boolean wordFitsRow(String word, int row) {
		if (word.length() >= Constants.WIDTH) {
			return false;
		}
		int currentField;

		for (int c = 0; c < word.length(); c++) {
			currentField = this.board[row][c];
			if (currentField != Constants.DEFAULT_EMPTY_CELL) {
				if (currentField != word.charAt(c)) {
					return false;
				}
			}

		}

		if (this.board[row][word.length()] == Constants.DEFAULT_EMPTY_CELL) {
			return true;
		} else {
			return false;
		}
	}

	public void insertIntoRow(String word, int row) {
		for (int i = 0; i < word.length(); i++) {
			board[row][i] = word.charAt(i);
		}
	}

	public void insertIntoColumn(String word, int column) {
		for (int i = 0; i < word.length(); i++) {
			board[i][column] = word.charAt(i);
		}
	}

	public boolean wordFitsColumn(String word, int column) {
		if (word.length() > Constants.HEIGHT) {
			return false;
		}
		int currentField;

		for (int r = 0; r < word.length(); r++) {
			currentField = this.board[r][column];
			if (currentField != Constants.DEFAULT_EMPTY_CELL) {
				if (currentField != word.charAt(r)) {
					return false;
				}
			}

		}

		if (word.length() == Constants.HEIGHT) {
			return true;
		}

		if (this.board[word.length()][column] == Constants.DEFAULT_EMPTY_CELL) {
			return true;
		} else {
			return false;
		}
	}

	public int[] findPosition() {
		for (int r = 0; r < Constants.HEIGHT; r++) {
			boolean isRowValid = false;
			if (r == 0) {
				if (!isRowFull(0) && !isRowFull(1)) {
					isRowValid = true;
				}
			} else if (r == Constants.HEIGHT - 1) {
				if (!isRowFull(Constants.HEIGHT - 1) && !isRowFull(Constants.HEIGHT - 2)) {
					isRowValid = true;
				}
			} else {
				if (!isRowFull(r) && !isRowFull(r - 1) && !isRowFull(r + 1)) {
					isRowValid = true;
				}
			}

			if (isRowValid) {
				return new int[] { r, 0, Constants.HORIZONTAL };
			}

			// if the row is filled with at least one word
//			int consecutiveEmpty = 0;
//			int emptyColumn;
//			for (int column = 0; column < Constants.WIDTH; column++) {
//				emptyColumn = column;
//				while (this.board[r][emptyColumn] == Constants.DEFAULT_EMPTY_CELL) {
//					consecutiveEmpty++;
//					if (consecutiveEmpty > 2) {
//						if (r == 0) {
//							if (this.board[r + 1][column + 1] == Constants.DEFAULT_EMPTY_CELL) {
//								return new int[] { r, column + 1, Constants.HORIZONTAL };
//							}
//						} else if (r == Constants.HEIGHT - 1) {
//							if (this.board[r - 1][column + 1] == Constants.DEFAULT_EMPTY_CELL) {
//								return new int[] { r, column + 1, Constants.HORIZONTAL };
//							}
//						} else {
//							if (this.board[r + 1][column + 1] == Constants.DEFAULT_EMPTY_CELL
//									&& this.board[r - 1][column + 1] == Constants.DEFAULT_EMPTY_CELL) {
//								return new int[] { r, column + 1, Constants.HORIZONTAL };
//							}
//						}
//					}
//					emptyColumn++;
//				}
//				consecutiveEmpty = 0;
//			}

		}

		for (int c = 0; c < Constants.WIDTH; c++) {
			boolean isColumnValid = false;
			if (c == 0) {
				if (!isColumnFull(0) && !isColumnFull(1)) {
					isColumnValid = true;
				}
			} else if (c == Constants.WIDTH - 1) {
				if (!isColumnFull(Constants.WIDTH - 1) && !isColumnFull(Constants.WIDTH - 2)) {
					isColumnValid = true;
				}
			} else {
				if (!isColumnFull(c) && !isColumnFull(c - 1) && !isColumnFull(c + 1)) {
					isColumnValid = true;
				}
			}

			if (isColumnValid) {
				return new int[] { 0, c, Constants.VERTICAL };
			}

		}
		return new int[] { -1 };
	}

	public boolean isRowFull(int row) {
		int consecutiveLetters = 0;
		for (int i = 0; i < Constants.WIDTH; i++) {
			if (consecutiveLetters >= 2) {
				return true;
			}
			if (this.board[row][i] != Constants.DEFAULT_EMPTY_CELL) {
				consecutiveLetters++;
			} else {
				consecutiveLetters = 0;
			}
		}
		return false;
	}

	public boolean isColumnFull(int column) {
		int consecutiveLetters = 0;
		for (int i = 0; i < Constants.HEIGHT; i++) {
			if (consecutiveLetters >= 2) {
				return true;
			}
			// if( column != 0 ) {
			// if(this.board[i][column-1] != Constants.DEFAULT_EMPTY_CELL) {
			// return true;
			// }
			// }

			if (this.board[i][column] != Constants.DEFAULT_EMPTY_CELL) {
				consecutiveLetters++;
			} else {
				consecutiveLetters = 0;
			}
		}
		return false;
	}

	public void displayBoard() {
		for (int r = 0; r < Constants.HEIGHT; r++) {
			for (int c = 0; c < Constants.WIDTH; c++) {
				System.out.print(this.board[r][c]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Board b = new Board();
		b.displayBoard();

		long startTime = System.nanoTime();
		if (b.bactracking() == false) {
			System.out.println("no solution");
		}
		long endTime = System.nanoTime();

		long duration = (endTime - startTime) / 1000; // to get milliseconds.

		System.out.println(duration);
		b.displayBoard();
		System.out.println(b.usedWords);
	}
}
