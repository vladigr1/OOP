package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<V> {
	private Set<V> vertices = new HashSet<>();
	private Map<V, Set<V>> edges = new HashMap<>();

	public void addVertex(V v) throws GraphException {
		if (vertices.contains(v) == true) {
			throw new GraphException("their is existed vertice");
		}
		vertices.add(v);
		Set<V> cur = new HashSet<>();
		cur.add(v);
		edges.put(v, cur);
	}

	public void addEdge(V v1, V v2) throws GraphException {
		if (vertices.contains(v1) == false || vertices.contains(v2) == false) {
			throw new GraphException("one of the edges doesnt exist");
		}
		if (edges.get(v1).contains(v2) == true) {
			throw new GraphException("fail Adding an existing edge");
		}
		Set<V> set1, set2;
		set1 = edges.get(v1);
		set2 = edges.get(v2);
		set1.add(v2);
		set2.add(v1);
		// Don't need to update get give a view
	}

	public boolean hasEdge(V v1, V v2) {
		Set<V> set = edges.get(v1);
		if (set != null && set.contains(v2) == true) {
			return true;
		}
		return false;
	}

	public boolean connected(V v1, V v2) throws GraphException {
		if (vertices.contains(v1) == false || vertices.contains(v2) == false) {
			throw new GraphException("vertices doesn't exist.");
		}
		Set<V> enter = new HashSet<>();
		return recConnected(v1, v2, enter);

	}

	private boolean recConnected(V v1, V v2, Set<V> enter) {
		// Don't need to test null the
		if (v1.equals(v2) == true) {
			return true;
		}
		if (enter.contains(v1) == true)
			return false;
		enter.add(v1);
		for (V neighbor : edges.get(v1)) {
			if (recConnected(neighbor, v2, enter) == true) {// in case of one true return
				return true;
			}
		}
		return false;
	}
}
