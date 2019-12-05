package iterator;

public class Fibonacci implements Iterator{

	private int upperBound, sumB,sumC;
	
	
	public Fibonacci(int upperBound) {
		this.upperBound = upperBound;
		sumB = 0;
		sumC = 1;
	}
	
	@Override
	public boolean hasNext() {
		if(sumC >= upperBound)return false;
		return true;
	}
	
	@Override
	public int next() {
		if(hasNext() == false) return sumB;
		sumC += sumB;
		sumB = sumC - sumB;
		return sumB;
	}
	
}
