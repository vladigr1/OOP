//vlad
public class Road {
	private City city1,city2;
	private int length;
	
	public Road(City city1, City city2, int length) {
		this.city1 = city1;
		this.city2 = city2;
		this.length = length;
		
		city1.connect(this);		//connect roads
		city2.connect(this);
	}
	
	public int getLength() {
		return this.length;
	}
	
	public City getCity1() {
		return this.city1;
	}
	
	public City getCity2() {
		return this.city2;
	}
}
