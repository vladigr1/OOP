import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class TestMain {
	private static boolean allGood = true;

	private static void check(boolean cond, String message) {
		if (!cond) {
			System.out.println("Problem: " + message);
			allGood = false;
		}
	}

	private static void checkEq(Object a, Object b, String message) {
		check(a.equals(b), String.format("%s: Expected \"%s\", but got \"%s\"",
				message, b, a));
	}

	private static void checkEqStr(Object a, Object b, String message) {
		checkEq(a.toString(), b.toString(), message);
	}

	private static void checkPublicMethods(Class<?> c, String[] methods)
	{
		Set<String> s = new HashSet<>();
		for (Method m : Object.class.getMethods())
			s.add(m.getName());
		for (String name : methods)
			s.add(name);
		for (Method m : c.getDeclaredMethods()) 
			if (!s.contains(m.getName())) 
				check(Modifier.isPrivate(m.getModifiers()), 
						"Non private extra method " +  c.getName() + "." + m.getName());

		for (Field f : c.getDeclaredFields()) 
			check(Modifier.isPrivate(f.getModifiers()), 
					"Non private field " +  c.getName() + "." + f.getName());
	}
	
	private static void testRooter() {		
		checkPublicMethods(Rooter.class, new String[] {"sqrt", "setPrecision"});
		Rooter r = new Rooter(0.01);
		double r2 = Math.sqrt(2);
		check(Math.abs(r.sqrt(2) - r2) <= 0.011, "Rooter.sqrt");
		r.setPrecision(0.00001);
		check(Math.abs(r.sqrt(2) - r2) <= 0.000011, "Rooter.sqrt");
	}

	private static void testCity() {
		checkPublicMethods(City.class, new String[] {"connect", "nearestCity"});
		checkPublicMethods(Road.class, new String[] {"getLength", "getCity1", "getCity2"});

		City karmiel = new City("Karmiel");
		City metula = new City("Metula");
		City telAviv = new City("Tel-Aviv");
		City jerusalem = new City("Jerusalem");

		checkEqStr(karmiel, "Karmiel", "City.toString");

		new Road(karmiel, metula, 50);
		new Road(karmiel, telAviv, 100);
		new Road(telAviv, jerusalem, 80);
		Road r = new Road(jerusalem, metula, 175);

		checkEq(r.getCity1(), jerusalem, "Road.getCity1");
		checkEq(r.getCity2(), metula, "Road.getCity2");
		checkEq(r.getLength(), 175, "Road.getLength");

		check(karmiel.nearestCity() == metula, "City.nearestCity");
	}

	private static void testCard() {
		checkPublicMethods(Card.class, new String[] {"compareTo", "getNum", "getSuit"});

		Card c = new Card(3, 2);
		checkEq(c.getNum(), 3, "Card.getNum");
		checkEq(c.getSuit(), 2, "Card.getSuit");
		Card c2 = new Card(4, 0);
		check(c.compareTo(c2) < 0, "Card.compareTo");
		checkEqStr(c, "3H", "Card.toString");
	}

	private static void testDeck() {
		checkPublicMethods(Deck.class, new String[] {"sort", "getNumCards", "takeOne"});

		Deck d1 = new Deck(3);
		checkEqStr(d1, "[0C, 0D, 0H, 0S, 1C, 1D, 1H, 1S, 2C, 2D, 2H, 2S]",
				"Deck.Deck(int)");
		Deck d2 = new Deck(d1, 4);
		checkEqStr(d2, "[2S, 2H, 2D, 2C]", "Deck.Deck(Deck, int)");
		Deck d3 = new Deck(d1, d2);
		checkEqStr(d3, "[1S, 2C, 1H, 2D, 1D, 2H, 1C, 2S, 0S, 0H, 0D, 0C]",
				"Deck.Deck(Deck, Deck)");
		check(d1.getNumCards() == 0 && d2.getNumCards() == 0,
				"Deck.Deck(Deck, Deck) not emptying parameters");
		d3.sort();
		checkEqStr(d3, "[0C, 0D, 0H, 0S, 1C, 1D, 1H, 1S, 2C, 2D, 2H, 2S]",
				"Deck.sort");
		checkEqStr(d3.takeOne(), "2S", "Deck.takeOne");
	}
	
	private static void testExDetails() {
		ExDetails s1 = ExDetails.firstStudent();
		ExDetails s2 = ExDetails.secondStudent();

		System.out.println("Check your personal details are correct here:");
		System.out.println(s1);
		if (s2 != null) {
			System.out.println(s2);
			System.out.format("Your zip file should be named : %d_%d.zip%n",
					s1.getId(), s2.getId());
		} else {
			System.out.format("Your zip file should be named : %d.zip%n",
					s1.getId());
		}
	}
		

	public static void main(String[] args) {
		testRooter();
		testCity();
		testCard();
		testDeck();
		if (allGood) {
			System.out.println("ALL SIMPLE TESTS PASSED");
			testExDetails();
		} else {
			System.out.println("--------------------------------------");
			System.out.println("SEE PROBLEMS ABOVE");
		}
	}

}
