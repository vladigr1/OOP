package game;

import java.util.Scanner;

public class FourInARow extends Game{

	public FourInARow(String player1, String player2) {
		super(6,7,new Player(player1,'W'),new Player(player2,'B'));
	}
	
	@Override
	protected boolean doesWin(int i, int j) {
		int res = maxLineContaining(i,j);
		if(res == 4)return true;
		return false;
	}
	
	@Override
	protected boolean onePlay(Player p) {
		int i,j;
		System.out.format("%s please enter column: ",p);
		if(s == null || s.hasNext() == false)s = new Scanner(System.in);
		i=s.nextInt();
		for(j=0;j<n;j++) {
			if(isEmpty(i, j) == true) {
				set(i,j,p);
				break;
			}
		}
		if(j == n) {
			System.out.println("The column is full...");
			return onePlay(p);
		}
		
		return doesWin(i, j);
	}
}
