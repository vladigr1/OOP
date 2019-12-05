package mines;

import java.util.Random;

public class Mines {

	private int numMines;
	private int height;
	private int width;
	private int opened; // count how much open isDone will o(1)
	private Square board[][];
	private boolean showAll;
	private Random rand = new Random();
	private StrategySquare func;

	public void setFunc(StrategySquare func) {
		this.func = func;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	public boolean CheckBomb(int i, int j) {
		return (board[i][j] != null && board[i][j].value == -1);
	}

	public Mines(int height, int width, int numMines) {
		this.height = height;
		this.width = width;
		this.numMines = numMines;
		board = new Square[height][width];
		opened = 0;
		showAll = false;
		int curNum = 0;
		func = null;
		while (curNum < numMines) {
			int i = Math.abs(rand.nextInt()) % height;
			int j = Math.abs(rand.nextInt()) % width;
			if (board[i][j] == null) {
				addMine(i, j);
				curNum++;
			}
		}
	}

	class Square {
		int value; // -1 = bomb other numbers mean open with number of
		int status; // 0 = close 1 = open -1 = flag

		public Square(int value, int status) {
			this.value = value;
			this.status = status;
		}
	}

	private boolean checkCordinate(int i, int j) {
		if (i < 0 || j < 0 || i >= height || j >= width)
			return false;
		return true;
	}

	private void calcSquare(int i, int j) {
		if (board[i][j] == null) {
			board[i][j] = new Square(1, 0);
		} else {
			if (board[i][j].value == -1)
				return;
			board[i][j].value++;
		}
	}

	public boolean addMine(int i, int j) {
		// dont have to check if their is a mine because we will set ther a mine
		// must check if it is on the board
		if (checkCordinate(i, j) == false)
			return false;
		board[i][j] = new Square(-1, 0);
		numMines++;
		if (checkCordinate(i - 1, j - 1) == true)
			calcSquare(i - 1, j - 1);
		if (checkCordinate(i - 1, j) == true)
			calcSquare(i - 1, j);
		if (checkCordinate(i - 1, j + 1) == true)
			calcSquare(i - 1, j + 1);
		if (checkCordinate(i, j - 1) == true)
			calcSquare(i, j - 1);
		if (checkCordinate(i, j + 1) == true)
			calcSquare(i, j + 1);
		if (checkCordinate(i + 1, j - 1) == true)
			calcSquare(i + 1, j - 1);
		if (checkCordinate(i + 1, j) == true)
			calcSquare(i + 1, j);
		if (checkCordinate(i + 1, j + 1) == true)
			calcSquare(i + 1, j + 1);
		return true;
	}

	public boolean open(int i, int j) { // the recursive open will only happen for null Squares
		if (checkCordinate(i, j) == false) // out of bound
			return false;

		if (board[i][j] != null) {
			if (board[i][j].status != 0) {
				if (board[i][j].value == -1) {
					return false;
				}
				return true;
			} else { // close square
				if (board[i][j].value == -1) {
					return false; // bomb
				}
				
				board[i][j].status = 1;
				if (func != null) { // code here must be shown
					func.handle(i, j);
				}
				// not a bomb and not recursive opened even if it had a flag because flag erase
				// 0 value square
				opened++;
				return true;
			}
		}
		// not near a bomb == value =0 , and it will open
		opened++;
		board[i][j] = new Square(0, 1);
		if (func != null) { // code here must be shown
			func.handle(i, j);
		}
		open(i - 1, j - 1);
		open(i - 1, j);
		open(i - 1, j + 1);
		open(i, j - 1);
		open(i, j + 1);
		open(i + 1, j - 1);
		open(i + 1, j);
		open(i + 1, j + 1);
		return true;
	}

	public void toggleFlag(int x, int y) {
		if (board[x][y] == null) {
			board[x][y] = new Square(0, 0);
		}
		if (board[x][y].status == -1) {
			board[x][y].status = 0;
			if (board[x][y].value == 0) { // you want to identify recursive open by nulls
				board[x][y] = null;
			}
		} else {
			board[x][y].status = -1;
		}
	}

	public boolean isDone() {
		return (opened + numMines == height * width);
	}

	public String get(int i, int j) {
		if (board[i][j] == null) { // all null in board are hidden showAll create a full Square board
			return ".";
		}
		if (showAll == true || board[i][j].status == 1) { // in case we need to show
			switch (board[i][j].value) {
			case -1:
				return "X";
			case 0:
				return " ";
			default: // opened with number of bombs
				return String.valueOf(board[i][j].value);
			}
		} else {
			if (board[i][j].status == -1) {
				return "F";
			}
			return ".";
		}

	}

	public void setShowAll(boolean showAll) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (board[i][j] == null) {
					board[i][j] = new Square(0, 0);
				}
			}
		}
		this.showAll = showAll;
	}

	public String toString() {
		StringBuilder build = new StringBuilder((width + 1) * height);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				build.append(get(i, j));
			}
			build.append('\n');
		}
		return build.toString();
	}
}
