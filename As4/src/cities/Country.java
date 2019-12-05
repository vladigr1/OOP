package cities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Country implements Comparable<Country> {

	private Set<City> cities;
	private String name;

	public Country(String name) {
		this.name = name;
		cities = new TreeSet<>();
	}

	public void addCity(City city) {
		if (equals(city.getCountry()) == false) {
			throw new IllegalArgumentException();
		}
		cities.add(city);
	}

	public int population() {
		int sum = 0;
		for (City city : cities) {
			sum += city.getPopulation();
		}
		return sum;
	}

	public List<City> smallCities(int under) {

		List<City> res = new ArrayList<>();
		for (City city : cities) { // create the list
			if (city.getPopulation() < under) {
				res.add(city);
			}
		}
		Collections.sort(res);
		return res;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Country other) {
		return name.compareTo(other.name);
	}

	@Override
	public boolean equals(Object other) {
		Country cur;
		if (other instanceof Country) {
			cur = (Country) other;
		} else {
			return false;
		}
		return this.compareTo(cur) == 0;
	}

	public String report() {
		StringBuilder res = new StringBuilder();
		res.append(name);
		res.append("(");
		res.append(population());
		res.append(") : ");
		for (City city : cities) { // we use tree set for giving it order
			res.append(city.getName());
			res.append("(");
			res.append(city.getPopulation());
			res.append("), ");
		}
		res.delete(res.length() - 2, res.length());
		return res.toString();
	}
}
