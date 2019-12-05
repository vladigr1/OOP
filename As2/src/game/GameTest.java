package game;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GameTest {
	public static boolean allGood = true;

	private static boolean check(boolean cond,
			String message) {
		if (!cond) {
			System.err.println("Problem: " + message);
			allGood = false;
			return false;
		}
		return true;
	}

	private static boolean checkEq(Object a, Object b,
			String message) {
		return check(a.equals(b), String.format(
				"%s: Expected \"%s\", but got \"%s\"",
				message, b, a));
	}

	private static boolean checkEqStr(Object a, Object b,
			String message) {
		return checkEq(a.toString(), b.toString(),
				message);
	}
	
	private static boolean checkSuper(Class<?> sub, Class<?> sup) {
		return check(sub.getSuperclass().equals(sup), sub.getName() + 
				" should be a direct subclass of " + sup.getName());
	}


	private static class AccessChecker {
		Set<String> pubM = new HashSet<>();
		Set<String> proM = new HashSet<>();
		Set<String> defM = new HashSet<>();

		Set<String> pubF = new HashSet<>();
		Set<String> proF = new HashSet<>();
		Set<String> defF = new HashSet<>();

		Set<String> pub, pro, def;
		Class<?> c;

		public AccessChecker(Class<?> c) {
			this.c = c;
		}

		public AccessChecker pM(String name) {
			pubM.add(name);
			return this;
		}

		public AccessChecker tM(String name) {
			proM.add(name);
			return this;
		}

		public AccessChecker dM(String name) {
			defM.add(name);
			return this;
		}

		public AccessChecker pF(String name) {
			pubF.add(name);
			return this;
		}

		public AccessChecker tF(String name) {
			proF.add(name);
			return this;
		}

		public AccessChecker dF(String name) {
			defF.add(name);
			return this;
		}

		private void checkAccess(String name, int m) {
			String pName = c.getName() + "." + name;
			if (Modifier.isPrivate(m))
				return;
			if (Modifier.isProtected(m))
				check(pro.contains(name), pName
						+ " should not be protected");
			else if (Modifier.isPublic(m))
				check(pub.contains(name),
						pName + " should not be public");
			else
				check(def.contains(name), pName
						+ " should not be package-private (default)");
		}

		public void checkAll() {
			pub = pubM;
			def = defM;
			pro = proM;
			for (Method m : c.getDeclaredMethods())
				checkAccess(m.getName(), m.getModifiers());
			pub = pubF;
			def = defF;
			pro = proF;
			for (Field f : c.getDeclaredFields())
				checkAccess(f.getName(), f.getModifiers());
		}

	}

	private static void setInput(String str) {
		InputStream inputStream = new ByteArrayInputStream(
				str.getBytes(Charset.forName("UTF-8")));
		System.setIn(inputStream);
	}
	
    private static PrintStream stdout = System.out;

	private static void ignoreOutput() {
		System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {}
        }));
	}
	
	private static void restoreOutput() {
		System.setOut(stdout);
	}
	
	private static void printAllGood() {
		String thisClass = MethodHandles.lookup()
				.lookupClass().getName();

		if (allGood)
			System.out
					.println(thisClass + " Test passed!");
		else {
			System.err.println(
					"-------------------------------------");
			System.err.println(thisClass
					+ " Tests failed, see above.");
		}
	}

	
	
	
// -------------------------------------------------------------------------------
// Testing games (question 3)

		private static void testPlayer() {
			new AccessChecker(Player.class).pM("getName").pM("getMark").pM("toString").checkAll();
			Player p = new Player("Test", 'X');
			checkEq(p.getName(), "Test", "Player.getName");
			checkEq(p.getMark(), 'X', "Player.getMark");
			checkEqStr(p, "Test(X)", "Player.toString");		
		}
		
		private static void testBoard() {
			new AccessChecker(Board.class).tF("n").tF("m").tF("board").pM("get").pM("toString").
				pM("isEmpty").pM("isFull").tM("set").tM("maxLineContaining").checkAll();
			
			Player p1 = new Player("Bibi", 'B');
			Player p2 = new Player("Gantz", 'G');
			Board b = new Board(3, 4);
			
			b.set(0, 0, p1);
			b.set(1, 0, p1);
			b.set(2, 2, p2);
			b.set(0, 0, p2);
			b.set(0, 1, p1);
			checkEq(b.get(1, 0), p1, "Board.get");
			checkEqStr(b, "BB..\nB...\n..G.\n", "Board.toString");
			checkEq(b.isEmpty(2,3), true, "Board.isEmpty");
			checkEq(b.isFull(), false, "Board.isFull");
			checkEq(b.maxLineContaining(1, 0), 2, "Board.maxLineContaining(1)");
			checkEq(b.maxLineContaining(2, 2), 1, "Board.maxLineContaining(2)");
		}
	
		private static void testGame() {
			new AccessChecker(Game.class).tF("players").tF("s").
				tM("onePlay").tM("doesWin").pM("play").checkAll();
			checkSuper(Game.class, Board.class);
			
			Player p1 = new Player("Red", 'R');
			Player p2 = new Player("Black", 'B');
			Game g;
			
			g = new Game(3, 4, p1, p2);
			g.set(0, 0, p1);
			check(g.doesWin(0, 0), "Game.doesWin");
			
			setInput("1 1  2 1");						
			g = new Game(3, 4, p1, p2);
			g.set(1, 1, p1);
			check(!g.onePlay(p2), "Game.onePlay");
					
			setInput("1 1   2 1    1 1    0 0");
			g = new Game(3, 4, p1, p2);
			checkEq(g.play(), p1, "Game.play");			
		}
		
		private static void testTicTacToe() {
			new AccessChecker(TicTacToe.class).tM("doesWin").checkAll();
			checkSuper(TicTacToe.class, Game.class);
			
			setInput("1 1  0 0  2 0  1 2 0 2");
			TicTacToe g = new TicTacToe("sponge", "bob");
			checkEqStr(g.play(), "sponge(X)", "TicTacToe");
		}
	
		private static void testFourInARow() {
			new AccessChecker(FourInARow.class).tM("doesWin").tM("onePlay").checkAll();
			checkSuper(FourInARow.class, Game.class);
		
			setInput("1  0  1  2  3  0  1  0 5  0");
			FourInARow g = new FourInARow("sponge", "bob");
			checkEqStr(g.play(), "bob(B)", "FourInARow");
		
		}
	
// -------------------------------------------------------------------------------
// main

	public static void main(String[] args) {
		ignoreOutput();
		testPlayer();
		testBoard();
		testGame();
		testTicTacToe();
		testFourInARow();
		restoreOutput();
		if (args != null)
			printAllGood();
	}
}
