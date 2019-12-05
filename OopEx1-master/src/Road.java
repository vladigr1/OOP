//vlad
public class Road {
	/*
	 * this class represent a road that connects two cities the road has a length as
	 * well
	 */

	private City city1, city2;
	private int length;

	// constructor
	public Road(City city1, City city2, int length) {
		this.city1 = city1;
		this.city2 = city2;
		this.length = length;
		city1.connect(this);
		city2.connect(this);

	}

	// getter
	public City getCity1() {
		return this.city1;
	}

	// getter
	public City getCity2() {
		return this.city2;
	}

	// getter
	public int getLength() {
		return this.length;
	}
}
