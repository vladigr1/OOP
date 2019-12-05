package graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Maze implements GraphInterface<Place> {

	private Place[][] places;
	private int size;

	public Maze(int size, int startx, int starty, int endx, int endy) {
		if (startx >= size || starty >= size || endx >= size || endy >= size || startx < 0 || starty < 0) {
			throw new IllegalArgumentException();
		}

		places = new Place[size][size];
		this.size = size;
		places[startx][starty] = new Place(startx, starty, size, 1);
		places[endx][endy] = new Place(endx, endy, size, 2);
	}

	public boolean addWall(int x, int y) {
		if (x >= size || y >= size) {
			throw new IllegalArgumentException();
		}
		if (places[x][y] != null) {
			return false;
		}
		places[x][y] = new Place(x, y, size);
		return true;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (places[i][j] == null) {
					res.append(".");
				} else {
					res.append(places[i][j].toString());
				}
			}
			res.append("\n");
		}
		return res.toString();
	}

	private void addEdge(Place cur, int i, int j, Graph<Place> graph) throws GraphException {
		if (i < 0 || j < 0) {
			// Can't check out for array bigger because the for in isSolvble block it
			return;
			// out of bound
		}
		Place neigbor = places[i][j];
		if (neigbor != null && neigbor.getRole() == 0) {
			return;
			// is a wall
		}
		if (neigbor == null) {
			neigbor = new Place(i, j, size, 3); // for only the test
		}
		if (graph.hasEdge(cur, neigbor) == false) {
			graph.addEdge(cur, neigbor);
		}
	}

	public boolean isSolvable() {
		boolean res = false;
		try {
			Graph<Place> graph = new Graph<>();
			Place start = null, end = null;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					Place cur = places[i][j];
					if (cur == null || cur.getRole() != 0) {
						// wall role = 0 we don't want to add wall as vertices
						// Can't be out of bound because of the way of for
						if (cur == null) {
							cur = new Place(i, j, size, 3);
						}
						if (cur.getRole() == 1) {// check roles
							start = cur;
						}
						if (cur.getRole() == 2) {// check roles
							end = cur;
						}
						graph.addVertex(cur);
						addEdge(cur, i, j - 1, graph);
						addEdge(cur, i - 1, j, graph);
						// we test backwards because we want to check from the one that exist
						// Doesn't need more because it scales down
					}
				}
			}
			res = graph.connected(start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}

	private boolean tesCordinate(int x, int y) {
		// return true in case it is possible neighbor
		if (x < 0 || x >= size || y < 0 || y >= size) {
			return false;
		}
		Place cur = places[x][y];
		if (cur == null || cur.getRole() != 0) {
			// not a wall
			return true;
		}
		// is a wall
		return false;
	}

	private void addNeighbor(int x, int y, Set<Place> neighbours) {
		Place neighbor = places[x][y];
		if (neighbor == null) {
			neighbor = new Place(x, y, size, 3);
		}
		neighbours.add(neighbor);
	}

	@Override
	public Collection<Place> neighbours(Place v) {
		int x = v.getX(), y = v.getY();
		Set<Place> neighbours = new HashSet<>();
		if (tesCordinate(x - 1, y) == true) {
			addNeighbor(x - 1, y, neighbours);
		}
		if (tesCordinate(x + 1, y) == true) {
			addNeighbor(x + 1, y, neighbours);
		}
		if (tesCordinate(x, y - 1) == true) {
			addNeighbor(x, y - 1, neighbours);
		}
		if (tesCordinate(x, y + 1) == true) {
			addNeighbor(x, y + 1, neighbours);
		}
		return neighbours;
	}

}
