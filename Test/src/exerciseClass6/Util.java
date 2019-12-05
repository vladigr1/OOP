package exerciseClass6;

public class Util {
	public static int sumScores(Scorer[] ss, int n) {
		try{
			int sum =0;
			for(Scorer s: ss) {
				sum += s.score(n);
			}
			return sum;
		}catch (ScorerException e) {
			throw new IllegalArgumentException();
		}
	}

}
