package graph;

import java.util.Random;

public class MyMain {
	public static void main(String [] args) {
		Random rand = new Random();
		Maze maze = new Maze(10,0, 0, 9, 9);
		for(int i=0; i<10;i++) {
			for(int j=0; j<10;j++) {
				if(rand.nextBoolean() == true) {
					maze.addWall(i, j);
				}
			}
		}
		System.out.println(String.valueOf(maze.isSolvable()));
		System.out.println(maze.toString());
	}
}
