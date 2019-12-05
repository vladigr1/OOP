package cities;

public class City implements Comparable<City> {

	private String name;
	private Country country;
	private int population;

	public City(String name, Country country, int population) {
		this.name = name;
		this.country = country;
		this.population = population;
	}

	public String getName() {
		return name;
	}

	public Country getCountry() {
		return country;
	}

	public int getPopulation() {
		return population;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(name);
		res.append(" (of ");
		res.append(country.toString());
		res.append(")");
		return res.toString();
	}

	@Override
	public int compareTo(City other) {
		if (country.equals(other.country) == false) {
			return country.compareTo(other.country); // use String compare
		}
		return name.compareTo(other.getName());
	}

	@Override
	public boolean equals(Object other) {
		City cur;
		if (other instanceof City) { // must check if it is a City for using cur
			cur = (City) other;
		} else {
			return false;
		}
		return this.compareTo(cur) == 0 ? true : false;
	}

}
