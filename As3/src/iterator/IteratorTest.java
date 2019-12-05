package iterator;

import main.Tester;
import main.Tester.OneTest;

public class IteratorTest {
	private Tester t;

	private abstract class OneTestIterator extends OneTest {
		public OneTestIterator(Tester tester, Class<?> currentClass, int minus,
				String testDesc) {
			tester.super(currentClass, minus, testDesc);
		}
		void checkIt(Iterator it, int[] a, String msg) {
			for (int i = 0; i < a.length; i++) {
				checkEq(it.hasNext(), true, msg + " : " + i + "-th hasNext() ");
				checkEq(it.next(), a[i], msg + " : " + i + "-th next() ");
			}
			checkEq(it.hasNext(), false, msg + "last hasNext() ");
		}
	}

	private void testArray() {
		Class<?> c = Array.class;
		t.checkImplements(c, Iterator.class);
		t.new AccessChecker(c).pM("next").pM("hasNext").checkAll();
		
		new OneTestIterator(t, c, 10,
				"array is {1, 3, 2}. iterating all, and then one more.") {
			@Override
			public void test() {
				int[] a = {1, 3, 2};
				Iterator it = new Array(a);
				checkIt(it, a, "array [1,3,2]");
				checkThrows(()->it.next(), IndexOutOfBoundsException.class, 
						"array [1,3,2] finished");
			}
		};
	}

	private void testFibonnaci() {
		Class<?> c = Fibonacci.class;
		t.checkImplements(c, Iterator.class);
		t.new AccessChecker(c).pM("next").pM("hasNext").checkAll();

		new OneTestIterator(t, c, 10,
				"initialized with 10. iterating all, and then one more.") {
			@Override
			public void test() {
				Iterator it = new Fibonacci(10);
				checkIt(it, new int[]{1, 1, 2, 3, 5, 8}, "Fibonacci(10)");
				checkEq(it.next(), 8,
						"After end, should still return last element.");
			}
		};
	}

	private void testIteratorToString() {
		Iterator it = new Fibonacci(20);
		t.new OneTest(IteratorToString.class, 10, "Fibonnaci(20) toString") {
			@Override
			public void test() {
			checkEq(IteratorToString.toString(it), "[1 1 2 3 5 8 13]",
				" when called with new Fibonnaci(20)");
		}};
	}

	// -------------------------------------------------------------------------------
	// main

	public Tester actualTest() {
		t = new Tester();
		testArray();
		testFibonnaci();
		testIteratorToString();
		return t;
	}

	public static Tester test() {
		return new IteratorTest().actualTest();
	}

	public static void main(String[] args) {
		Tester.forStudents = true;
		Tester.ignoreOutput();
		Tester t = test();
		Tester.restoreOutput();
		t.printAllGood("TreeTest");
	}

}