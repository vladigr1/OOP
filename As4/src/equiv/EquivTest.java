package equiv;

import main.Tester;

public class EquivTest {

	private Tester t;

	private void testEquiv() {
		Class<?> c = Equiv.class;
		t.new OneTest(c, 10, "Published Test") {
			@Override
			public void test() {
				Equiv<String> equiv = new Equiv<>();
				equiv.add("ball", "balloon");
				equiv.add("child", "person");
				equiv.add("girl", "child");
				equiv.add("ball", "sphere");
				equiv.add("sphere", "circle");
				equiv.add("dog", "cat");

				check(equiv.are("balloon", "circle"), "are(ballon, circle)");
				check(equiv.are("child", "girl"), "are(child, girl)");
				check(equiv.are("sun", "sun"), "are(sun, sun)");
				check(!equiv.are("dog", "ball"), "are(dog, ball)");
				check(!equiv.are("table", "dog"), "are(table, dog)");
			}
		};
	}

	// -------------------------------------------------------------------------------
	// main

	public Tester actualTest() {
		t = new Tester();
		testEquiv();
		return t;
	}

	public static Tester test() {
		return new EquivTest().actualTest();
	}

	public static void main(String[] args) {
		Tester.ignoreOutput();
		Tester t = test();
		Tester.restoreOutput();
		t.printAllGood("EquivTest");
	}
}
