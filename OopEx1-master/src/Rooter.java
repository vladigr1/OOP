//eldad
public class Rooter {

	private double precision;

	// constructor
	public Rooter(double precision) {
		this.precision = precision;
	}

	// setter
	public void setPrecision(double precision) {
		this.precision = precision;
	}

	// search for a good square root of a value x
	public double sqrt(double x) {
		double one, two; // the two candidates
		double sum = x; // stands for the sum of one and two , but starts at x
		do {
			one = sum / 2;
			two = x / one;
			sum = one + two;
		} while (Math.abs(one - two) >= precision); // checks that the distance is less then precision
		return one;
	}
}
