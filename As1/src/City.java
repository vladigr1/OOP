//vlad
public class City {

	private String name;
	private Road[] roads= new Road[10];
	private int numRoads;
	
	public City(String name) {
		this.name = name;
		//numRoads = 0
	}
	
	public void connect(Road r) {
		if(numRoads == 10) {						//the Array is full
			System.out.println("The array is full.\n");
			return;					
		}
		this.roads[numRoads] = r;
		this.numRoads++;
	}
	
	public City nearestCity() {
		if(this.numRoads == 0) return null;
		Road minRoad = roads[0];
		for(int i =1;i < this.numRoads;++i) {		//finding shortest road
			if( roads[i].getLength() < minRoad.getLength() ) minRoad = roads[i]; 
		}
		
		if(minRoad.getCity1() != this) {			//finding the correct city to return
			return minRoad.getCity1();
		}
			return minRoad.getCity2();
	}
	
	public String toString() {
		return name;
	}
}
