package cities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class World {

	private Map<String, Country> countries = new TreeMap<>();

	public void addCountry(String name) {
		countries.put(name, new Country(name));
	}

	public void addCity(String name, String countryName, int population) {

		Country cur = countries.get(countryName);
		if (cur == null) {
			throw new IllegalArgumentException();
		}
		cur.addCity(new City(name, cur, population));
	}

	public int population() {
		Collection<Country> cur = countries.values();
		int sum = 0;
		for (Country country : cur) {
			sum += country.population();
		}
		return sum;
	}

	public List<City> smallCities(int under) {
		Collection<Country> cur = countries.values();
		List<City> res = new ArrayList<>();
		for (Country country : cur) {
			res.addAll(country.smallCities(under));
		}
		Collections.sort(res);
		return res;
	}

	public String report() {
		StringBuilder res = new StringBuilder();
		Collection<Country> cur = countries.values();
		for (Country country : cur) {	//using treemap for it be order
			res.append(country.report());
			res.append("\n");
		}
		res.append("Total population is ");
		res.append(population());
		res.append("\n");
		return res.toString();
	}
}
