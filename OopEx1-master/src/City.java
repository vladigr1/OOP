//eldad
public class City {

	/**
	 * this class represent a city that has roads that leads into and out from the
	 * city the name of the city and the number of roads
	 */

	private Road[] roads;
	private int numRoads;
	private String name;

	// constructor
	public City(String name) {
		this.name = name;
		this.numRoads = 0;
		this.roads = new Road[10];
	}

	// constructor
	public void connect(Road r) {
		/*
		 * adds the road to the array of roads
		 */
		try {
			roads[this.numRoads++] = r;
		} catch (Exception e) {
			System.out.println("too many roads in the array");
		}
	}

	// returns a reference to the city with the shortest road leading into it.
	public City nearestCity() {
		// in case it is empty
		if (numRoads == 0)
			return null;
		// initialize the a minimum value
		City closest = roads[0].getCity1() == this ? roads[0].getCity2() : roads[0].getCity1();
		int distance = roads[0].getLength();
		// check each other road
		for (int i = 1; i < this.numRoads; i++)
			if (roads[i].getLength() < distance) {
				distance = roads[i].getLength();
				closest = roads[i].getCity1() == this ? roads[i].getCity2() : roads[i].getCity1();
			}

		return closest;
	}

	// printing format is only it's name
	public String toString() {
		return this.name;
	}

}
