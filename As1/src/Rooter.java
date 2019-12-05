//vlad
public class Rooter {
	private double precision;
	public Rooter(double precision) {
		this.setPrecision(precision);
	}
	public void setPrecision(double precision){
		this.precision = precision;
	}
	
	public double sqrt(double x) {
		double one = x/2, two;
		two = x/one;
		while( (two > one ? two - one : one - two ) >= this.precision) {
		one = (two + one) / 2;
		two = x/one;
		}
		return one;
	}
}
