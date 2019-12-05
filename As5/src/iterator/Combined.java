package iterator;

import java.util.Iterator;

public class Combined<E> implements Iterable<E>{
	Iterable<E> first,second;
	public Combined(Iterable<E> first, Iterable<E> second) {
		this.first = first;
		this.second = second;
	}
	@Override
	public Iterator<E> iterator(){
		return new Iterator<E>(){
			private Iterator<E> iter1 = first.iterator() ;
			private Iterator<E> iter2 = second.iterator();
			private Iterator<E> curIterator = iter1;
			
			@Override
			public boolean hasNext(){
				return (iter1.hasNext() || iter2.hasNext() );
			}
			
			private void setCurIter() {
				if(curIterator == iter1) {
					curIterator = iter2;
				}else {
					curIterator = iter1;
				}
			}
			
			@Override
			public E next() {
				E res;
				if(curIterator.hasNext() == false) {
					setCurIter();
					res = curIterator.next();
				}else {
					// curIterator not empty
					res = curIterator.next();
					setCurIter();
				}
				return res;
			}
		
		};
	}

}
