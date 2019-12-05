package exerciseClass6;

public class Class6{
	public static void main(String[] args) {
		try {
		Scorer[] ss = new Scorer[3];
		ss[0] = new DivideScorer(2);     
		ss[1] = new DivideScorer(3);     
		ss[2] = new SquareScorer();     
		System.out.println(Util.sumScores(ss, 81));
		}
		catch(ScorerException e) {
			throw new IllegalArgumentException();
		}
	}
}
