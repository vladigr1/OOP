package circuits;

import main.Tester;
import main.Tester.AccessChecker;

public class CircuitsTest {
	private Tester t;

	private void testCircuitException() {
		Class<?> c = CircuitException.class;
		t.checkSuper(c, Exception.class);	
	}
	
	private void testGate() {
		t.new AccessChecker(Gate.class).pM("calc").pM("toString").pM("getName")
				.tM("func").pM("simplify").tF("inGates").abs("simplify")
				.abs("func").abs("getName").checkAll();
	}

	private void testGateSubClass(Class<?> c, boolean instance) {
		t.checkSuper(c, Gate.class);
		AccessChecker ac = t.new AccessChecker(c).pM("getName").tM("func")
				.pM("simplify");
		if (instance)
			ac.pM("instance");
		ac.checkAll();
	}

	private void testTrueGate() {
		Class<?> c = TrueGate.class;
		testGateSubClass(c, true);
		t.new OneTest(c, 10, null) {
			@Override
			public void test() throws CircuitException {
				Gate g = TrueGate.instance();
				Gate g2 = TrueGate.instance();
				check(g == g2, "should be a singleton.");
				checkEq(g.getName(), "T", "getName");
				checkEqStr(g, "T", "toString");
				checkEq(g.calc(), true, "calc");
			}
		};
	}

	private void testFalseGate() {
		Class<?> c = FalseGate.class;
		testGateSubClass(c, true);
		t.new OneTest(c, 10, null) {
			@Override
			public void test() throws CircuitException {
				Gate g = FalseGate.instance();
				Gate g2 = FalseGate.instance();
				check(g == g2, "should be a singleton.");
				checkEq(g.getName(), "F", "getName");
				checkEqStr(g, "F", "toString");
				checkEq(g.calc(), false, "calc");
			}
		};
	}

	private void testAndGate() {
		Class<?> c = AndGate.class;
		testGateSubClass(c, false);
		t.new OneTest(c, 10, null) {
			@Override
			public void test() throws CircuitException {
				Gate g = new AndGate(
						new Gate[]{TrueGate.instance(), FalseGate.instance()});
				checkEq(g.getName(), "AND", "getName");
				checkEqStr(g, "AND[T, F]", "toString");
				checkEq(g.calc(), false, "calc");
			}
		};
		
	}

	private void testOrGate() {
		Class<?> c = OrGate.class;
		testGateSubClass(c, false);
		t.new OneTest(c, 10, null) {
			@Override
			public void test() throws CircuitException {
				Gate g = new OrGate(
						new Gate[]{TrueGate.instance(), FalseGate.instance()});
				checkEq(g.getName(), "OR", "getName");
				checkEqStr(g, "OR[T, F]", "toString");
				checkEq(g.calc(), true, "calc");
			}
		};
		
	}

	private void testNotGate() {
		Class<?> c = NotGate.class;
		testGateSubClass(c, false);
		t.new OneTest(c, 10, null) {
			@Override
			public void test() throws CircuitException {
				Gate g = new NotGate(FalseGate.instance());
				checkEq(g.getName(), "NOT", "getName");
				checkEq(g.calc(), true, "calc");
			}
		};
	}

	private void testAnd2Gate() {
		Class<?> c = And2Gate.class;
		t.checkSuper(c, AndGate.class);
		t.new AccessChecker(c).checkAll();
	}

	private void testOr2Gate() {
		Class<?> c = Or2Gate.class;
		t.checkSuper(c, OrGate.class);
		t.new AccessChecker(c).checkAll();
	}

	private void testVarGate() {
		Class<?> c = VarGate.class;
		t.checkSuper(c, Gate.class);
		t.new AccessChecker(c).pM("getName").tM("func").pM("simplify")
				.pM("setVal").checkAll();

		t.new OneTest(c, 10, "varGate") {
			@Override
			public void test() throws CircuitException {
				VarGate g = new VarGate("blue");
				checkEq(g.getName(), "Vblue", "getName");	
				try {
					g.calc();
					check(false, "should throw exception when var not set.");
				} catch (CircuitException e) {
				}
				g.setVal(true);
				checkEq(g.calc(), true, "check2");
				g.setVal(false);
				checkEq(g.calc(), false, "check3");
			}
		};
	}

	private void testCalc() {
		t.new OneTest(CircuitsTest.class, 10, null) {
			@Override
			public void test() throws CircuitException {
				VarGate v1 = new VarGate("1");
				VarGate v2 = new VarGate("2");
				Gate g1 = new Or2Gate(FalseGate.instance(), TrueGate.instance());
				Gate g2 = new Or2Gate(v1, new NotGate(v2));
				Gate out = new AndGate(new Gate[]{g1, g2, TrueGate.instance()});
				v1.setVal(false);
				v2.setVal(true);
				
				checkEqStr(out, "AND[OR[F, T], OR[V1, NOT[V2]], T]", "toString of example circuit wrong.");
				checkEqStr(out.calc(), "false", "calc of example circuit wrong.");
			}
		};
	}
	

	private void testSimplify() {
		t.new OneTest(CircuitsTest.class, 10, null) {
			@Override
			public void test() throws CircuitException {
				VarGate v1 = new VarGate("1");
				VarGate v2 = new VarGate("2");
				Gate g1 = new Or2Gate(FalseGate.instance(), TrueGate.instance());
				Gate g2 = new Or2Gate(v1, new NotGate(v2));
				Gate out = new AndGate(new Gate[]{g1, g2, TrueGate.instance()});
				
				checkEqStr(out.simplify(), "OR[V1, NOT[V2]]", "simplify of example circuit wrong.");	
				checkEqStr(out, "AND[OR[F, T], OR[V1, NOT[V2]], T]", "circuit should not change because of simplify");
				
				v1.setVal(false);
				checkEqStr(out.simplify(), "NOT[V2]", "second simplify of example circuit wrong.");	
			}
		};
		}

	// t.checkSuper(NamedTasks.class, Tasks.class);

	// -------------------------------------------------------------------------------
	// main

	public Tester actualTest() {
		t = new Tester();
		testCircuitException();
		testGate();
		testTrueGate();
		testFalseGate();
		testAndGate();
		testOrGate();
		testNotGate();
		testAnd2Gate();
		testOr2Gate();
		testVarGate();
		testCalc();
		
		testSimplify();
		
		return t;
	}

	public static Tester test() {
		return new CircuitsTest().actualTest();
	}

	public static void main(String[] args) {
		Tester.forStudents = true;
		Tester.ignoreOutput();
		Tester t = test();
		Tester.restoreOutput();
		t.printAllGood("CircuitsTest");
	}
}