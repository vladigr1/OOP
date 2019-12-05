package graph;

import java.util.HashSet;
import java.util.Set;

public class ConnectionChecker<V> {

	private GraphInterface<V> g;

	public ConnectionChecker(GraphInterface<V> g) {
		this.g = g;
	}

	public boolean check(V v1, V v2) {
		if (v1 == null || v2 == null)	//bad
			return false;
		Set<V> enter = new HashSet<V>();
		return recCheck(v1, v2, enter);
	}

	private boolean recCheck(V v1, V v2, Set<V> enter) {
		if (v1.equals(v2) == true) {
			return true;
		}
		if (enter.contains(v1) == true) {
			return false;
		}

		enter.add(v1);
		for (V neighbour : g.neighbours(v1)) {
			if (recCheck(neighbour, v2, enter) == true) {
				return true;
			}
		}
		return false;
	}

}
