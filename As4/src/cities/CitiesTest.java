package cities;

import main.Tester;

public class CitiesTest {

	private Tester t;

	private void testCity() {
		Class<?> c = City.class;
		t.new AccessChecker(c).pM("getName").pM("getCountry").pM("getPopulation").pM("toString").pM("equals")
				.pM("compareTo").checkAll();
		t.new OneTest(c, 10, "Basic stuff") {
			@Override
			public void test() {
				Country country = new Country("USA");
				City c = new City("Chicago", country, 10);
				checkEq(c.getCountry(), country, "getCountry");
				checkEq(c.getName(), "Chicago", "getName");
				checkEq(c.getPopulation(), 10, "getName");
				checkEq(c.toString(), "Chicago (of USA)", "toString");
				City c2 = new City("Chicago", country, 100);
				checkEq(c, c2, "cities with same name and same country should be equal.");
			}
		};
	}

	private void testCountry() {
		Class<?> c = Country.class;
		t.new AccessChecker(c).pM("toString").pM("smallCities").pM("addCity").pM("population").pM("report")
				.pM("compareTo").pM("equals").pM("hashCode").checkAll();

		t.new OneTest(c, 10, "Basic stuff") {
			@Override
			public void test() {
				Country country = new Country("USA");
				City c = new City("Chicago", country, 10);
				City c2 = new City("Detroit", country, 100);
				country.addCity(c);
				country.addCity(c2);
				checkEq(country.population(), 110, "population()");
				checkEq(country.report(), "USA(110) : Chicago(10), Detroit(100)", "report()");

				checkEq(country.smallCities(40).toString(), "[Chicago (of USA)]", "smallCities()");

				City c3 = new City("Chicago", country, 1000);
				country.addCity(c3);
				checkEq(country.report(), "USA(110) : Chicago(10), Detroit(100)",
						"adding a copy of the same city should not work");

				City c4 = new City("Istanbul", new Country("Turkey"), 1);
				checkThrows(() -> {
					country.addCity(c4);
				}, IllegalArgumentException.class, "adding a city of a different country");
			}
		};
	}

	private void testWorld() {
		Class<?> c = World.class;
		t.new AccessChecker(c).pM("toString").pM("addCity").pM("report").pM("smallCities").pM("population")
				.pM("addCountry").checkAll();

		t.new OneTest(c, 10, "Example in published exercise") {
			@Override
			public void test() {
				World w = new World();
				w.addCountry("Brazil");
				w.addCity("Salvador", "Brazil", 2677000);
				w.addCountry("Spain");
				w.addCity("Barcelona", "Spain", 1615000);
				w.addCity("Rio de Janeiro", "Brazil", 6320000);
				w.addCity("Granada", "Spain", 233764);

				checkEqStr(w.report(), "Brazil(8997000) : Rio de Janeiro(6320000), Salvador(2677000)\n"
						+ "Spain(1848764) : Barcelona(1615000), Granada(233764)\n" + "Total population is 10845764\n",
						"report()");
				checkEq(w.smallCities(2000000).toString(), "[Barcelona (of Spain), Granada (of Spain)]", "smallCities");
			}
		};
	}

	// -------------------------------------------------------------------------------
	// main

	public Tester actualTest() {
		t = new Tester();
		testCity();
		testCountry();
		testWorld();
		return t;
	}

	public static Tester test() {
		return new CitiesTest().actualTest();
	}

	public static void main(String[] args) {
		Tester.ignoreOutput();
		Tester t = test();
		Tester.restoreOutput();
		t.printAllGood("CitiesTest");
	}
}
