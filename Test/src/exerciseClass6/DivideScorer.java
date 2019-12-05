package exerciseClass6;

public class DivideScorer implements Scorer {
	
	private int d;
	
	public DivideScorer(int d) throws ScorerException{
		if(d <= 0) throw new ScorerException();
		this.d = d;
	}
	
	@Override
	public int score(int n)  { 
		return (Math.abs(n) % d == 0)? 1:0;
	}
}
