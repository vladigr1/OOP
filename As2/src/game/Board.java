package game;

public class Board{
	
	protected Player[][] board;
	protected int n,m;
	
	public Board(int n, int m) {
		board = new Player[n][m];
		this.n =n;
		this.m =m;
	}
	
	protected boolean set(int i, int j, Player p) {
		if(board[i][j] != null) return false;
		board[i][j] = p;
		return true;
	}
	
	public boolean isEmpty(int i, int j) {
		if(board[i][j] == null) return true;
		return false;
	}
	
	public Player get(int i, int j) {
		return board[i][j];			//if it empty then their is null
	}
	
	public boolean isFull() {
		int i,j;
		for(i=0;i<n;i++) {
			for(j=0;j<m;j++) {
				if(isEmpty(i,j))return false;
			}
		}
		return true;
	}
	
	public String toString() {
		int i,j;
		String ret = "";
		for(i=0;i<n;i++) {
			for(j=0;j<m;j++) {
				if(isEmpty(i,j) ) {
					ret += '.';
				}else {
					ret += board[i][j].getMark() ;
				}
			}
			ret += '\n';
		}
		return ret;
	}
	private int upContaining(int i,int j) {
		Player player = board[i][j];
		int k = j +1,count = 1;
		while(k<m && board[i][k] == player) {	//upper
			count++;
			k++;
		}
		k = j - 1;
		while(k>=0 && board[i][k] == player) {	//lower
			count++;
			k--;
		}
		return count;
	}
	
	private int leftContaining(int i,int j) {
		Player player = board[i][j];
		int k = i+1,count = 1;
		while(k<n && board[k][j] == player) {	//right
			count++;
			k++;
		}
		k = i - 1;
		while(k>=0 && board[k][j] == player) {	//left
			count++;
			k--;
		}
		return count;
	}
	
	private int diagonalRightDownContaining(int i,int j) {
		Player player = board[i][j];
		int k = i+1, l = j +1 ,count = 1;
		while(k<n && l <m && board[k][l] == player) {	//right down
			count++;
			k++;
			l++;
		}
		k = i - 1;
		l = j - 1;
		while(k>= 0 && l >= 0 && board[k][l] == player) {	//left up
			count++;
			k--;
			l--;
		}
		return count;
	}
	
	private int diagonalRightUpContaining(int i,int j) {
		Player player = board[i][j];
		int k = i+1, l = j -1 ,count = 1;
		while(k<n && l >=0 && board[k][l] == player) {	//right up
			count++;
			k++;
			l--;
		}
		k = i - 1;
		l = j + 1;
		while(k>= 0 && l < m && board[k][l] == player) {	//left up
			count++;
			k--;
			l++;
		}
		return count;
	}
	
	protected int maxLineContaining(int i, int j) {
		int max = upContaining(i, j);
		if (max < leftContaining(i,j) )max = leftContaining(i,j);
		if (max < diagonalRightDownContaining(i,j) )max = diagonalRightDownContaining(i,j);
		if (max < diagonalRightUpContaining(i,j) )max = diagonalRightUpContaining(i,j);
		return max;
	}
}
