package iterator;

import java.lang.IndexOutOfBoundsException;

public class Array implements Iterator{
	
	private int arr[], index;
	
	public Array(int []arr){
		this.arr = arr;
		index =0;
	}
	
	@Override
	public boolean hasNext() {
		if(index != arr.length) return true;
		return false;
	}

	@Override
	public int next() {
		if(this.hasNext() == false) throw new IndexOutOfBoundsException() ;
		return arr[index++];
	}
	
}
