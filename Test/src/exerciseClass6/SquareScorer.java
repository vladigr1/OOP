package exerciseClass6;

public class SquareScorer implements Scorer{
	
	public SquareScorer() {
	}
	
	@Override
	public int score(int n) throws ScorerException{
		if (n < 0) throw new ScorerException();
		return (Math.pow(Math.sqrt(n), 2) == n)? 3:0;
	}
	
}
