package game;

public class TicTacToe extends Game{
	public TicTacToe(String player1, String player2) {
		super(3,3,new Player(player1,'X'),new Player(player2,'0'));
	}
	
	@Override
	protected boolean doesWin(int x, int y) {
		int res = maxLineContaining(x,y); 
		if(res == 3)return true;
		return false;
	}
}
