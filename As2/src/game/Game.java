package game;

import java.util.Scanner;

public class Game extends Board {
	protected Player[] players;
	protected Scanner s;
	
	public Game(int n,int m, Player p1, Player p2){
		super(n,m);
		players = new Player[2];
		players[0] = p1;
		players[1] = p2;
	}
	
	protected boolean doesWin(int i, int j) {
		if(i == 0 && j ==0) return true;
		return false;
	}
	
	protected boolean onePlay(Player p) {
		int i,j;
		System.out.format("%s please enter x and y: ",p);
		if(s == null || s.hasNext() == false)s = new Scanner(System.in);
		i=s.nextInt();
		j=s.nextInt();
		if(isEmpty(i, j) == false) {
			System.out.println("There is a piece there already...");
			return onePlay(p);
		}
		set(i,j,p);
		return doesWin(i, j);
	}
	
	public Player play() {
		Player curPlayer = players[1];
		do {
			curPlayer = curPlayer == players[0] ? players[1]: players[0]; //switching players
		}while(onePlay(curPlayer) == false);
		return curPlayer;
	}
	

}
