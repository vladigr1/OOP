package iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TwoArrays implements Iterable<Integer> {
	private List<Integer> list;
	private int totalSize;

	public TwoArrays(int[] a1, int[] a2) {
		int max = Math.max(a1.length, a2.length);
		totalSize = a1.length+a2.length;
		list = new ArrayList<Integer>(totalSize);
		//knowing the size shorter the time to build the list
		for(int i=0; i < max; i++) {
			if(i<a1.length) list.add(a1[i]);
			if(i<a2.length) list.add(a2[i]);
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>(){
			private int i=0;
			
			@Override
			public boolean hasNext() {
				return (i < totalSize);
			}

			@Override
			public Integer next() {
			return list.get(i++);
			}
			
		};
	}

}
