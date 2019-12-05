package equiv;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Equiv<E> {
	private List<Set<E>> equivs = new LinkedList<>();

	public void add(E e1, E e2) {
		Set<E> set1 = null, set2 = null;
		for (Set<E> set : equivs) {
			if (set.contains(e1)) {
				set1 = set;
			}
			if (set.contains(e2)) {
				set2 = set;
			}
		}

		// must check all cases if one is on the equivs
		if (set1 == null && set2 == null) {
			set1 = new HashSet<>();
			set1.add(e1);
			set1.add(e2);
			equivs.add(set1);
		}
		if (set1 != null && set2 == null) {
			set1.add(e2);
		}
		if (set1 == null && set2 != null) {
			set2.add(e1);
		}
		if (set1 != null && set2 != null) {
			set1.addAll(set2);
			if (set1.equals(set2) == false)
				equivs.remove(set2);
		}
	}

	public boolean are(E e1, E e2) {

		// reflection
		if (e1.equals(e2))
			return true;

		for (Set<E> set : equivs) {
			if (set.contains(e1)) {
				if (set.contains(e2)) {
					return true;
				} else { // we dont need to check the other cases
					return false;
				}
			}
		}
		return false;
	}

}
