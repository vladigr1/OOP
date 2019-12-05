package graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import main.Tester;

public class GraphTest {

	private Tester t;

	private Maze simpleMaze() {
		Maze m = new Maze(4, 0, 0, 3, 3);
		m.addWall(1, 1);
		m.addWall(3, 1);
		m.addWall(0, 1);
		m.addWall(2, 3);
		m.addWall(2, 3);
		m.addWall(1, 3);
		return m;
	}

	private void testPlace() {
		Class<?> c = Place.class;
		t.new OneTest(c, 10, "Simple test") {
			@Override
			public void test() {
				Place p = new Place(3, 10, 20);
				checkEq(p.getX(), 3, "getX");
				checkEq(p.getY(), 10, "geyY");
				checkThrows(() -> new Place(3, 10, 8), IllegalArgumentException.class, "y too large");
				checkThrows(() -> new Place(10, 3, 8), IllegalArgumentException.class, "x too large");
			}
		};
	}

	private void testMaze() {
		Class<?> c = Maze.class;
		t.new OneTest(c, 10, "Published Test") {
			@Override
			public void test() {
				Maze m = simpleMaze();
				checkEqStr(m, "S@..\n" + ".@.@\n" + "...@\n" + ".@.E\n", "");
			}
		};
	}

	private void testGraphException() {
		t.checkSuper(GraphException.class, Exception.class);
	}

	private void testGraph() {
		Class<?> c = Graph.class;
		t.new AccessChecker(c).pM("connected").pM("addEdge").pM("addVertex").pM("hasEdge").checkAll();
		t.new OneTest(c, 10, "Basic test") {
			@Override
			public void test() throws GraphException {
				Graph<String> g = new Graph<>();
				g.addVertex("A");
				g.addVertex("B");
				g.addEdge("A", "B");
				g.addVertex("C");
				g.addEdge("C", "B");
				g.addVertex("D");
				g.addVertex("E");
				g.addEdge("D", "E");

				checkThrows(() -> g.addVertex("A"), GraphException.class, "Adding an existing vertex");
				checkThrows(() -> g.addEdge("B", "C"), GraphException.class, "Adding an existing edge");
				checkThrows(() -> g.addEdge("B", "F"), GraphException.class, "Adding an edge with non-existent vertex");
				check(g.connected("A", "C"), "connected 1");
				check(g.connected("C", "A"), "connected 2");
				check(!g.connected("D", "A"), "connected 3");
				checkThrows(() -> g.connected("A", "W"), GraphException.class, "connected() with non-existent vertex");

			}
		};
	}

	private void testMaze2() {
		Class<?> c = Maze.class;
		t.new OneTest(c, 10, "checking isSolvable()") {
			@Override
			public void test() {
				Maze m = simpleMaze();

				check(m.isSolvable(), "");
				m.addWall(2, 2);
				check(!m.isSolvable(), "");
			}
		};
	}

	private static class GIExample implements GraphInterface<Integer> {
		@Override
		public Collection<Integer> neighbours(Integer v) {
			if (v == 0)
				return Arrays.asList(1, 2);
			else if (v == 1)
				return Arrays.asList(0);
			else if (v == 2)
				return Arrays.asList(0, 4);
			else if (v == 4)
				return Arrays.asList(2);
			else
				return Collections.emptyList();
		}
	}

	private void testConnectionChecker() {
		Class<?> c = ConnectionChecker.class;
		t.new AccessChecker(c).pM("check").checkAll();

		t.new OneTest(c, 10, "Example") {
			@Override
			public void test() {
				GraphInterface<Integer> g = new GIExample();
				ConnectionChecker<Integer> cc = new ConnectionChecker<>(g);
				check(cc.check(1, 4), "check(1, 4)");
				check(!cc.check(3, 4), "check(3, 4)");
			}
		};
	}

	private void testMaze3() {
		Class<?> c = ConnectionChecker.class;
		t.new AccessChecker(c).pM("check").checkAll();

		t.new OneTest(c, 10, "Example") {
			@Override
			public void test() {
				Maze m = simpleMaze();
				ConnectionChecker<Place> cc = new ConnectionChecker<>(m);
				check(cc.check(new Place(0, 0, 4), new Place(3, 3, 4)), "");
			}
		};
	}

	// -------------------------------------------------------------------------------
	// main

	public Tester actualTest() {
		t = new Tester();
		testPlace();
		testMaze();
		testGraphException();
		testGraph();
		testMaze2();
		testConnectionChecker();
		testMaze3();

		return t;
	}

	public static Tester test() {
		return new GraphTest().actualTest();
	}

	public static void main(String[] args) {
		Tester.ignoreOutput();
		Tester t = test();
		Tester.restoreOutput();
		t.printAllGood("GraphTest");
	}
}
