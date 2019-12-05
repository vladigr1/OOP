package tasks;


import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TasksTest {
	public static boolean allGood = true;

	private static boolean check(boolean cond,
			String message) {
		if (!cond) {
			System.err.println("Problem: " + message);
			allGood = false;
			return false;
		}
		return true;
	}

	private static boolean checkEq(Object a, Object b,
			String message) {
		return check(a.equals(b), String.format(
				"%s: Expected \"%s\", but got \"%s\"",
				message, b, a));
	}

	private static boolean checkEqStr(Object a, Object b,
			String message) {
		return checkEq(a.toString(), b.toString(),
				message);
	}

	private static boolean checkSuper(Class<?> sub, Class<?> sup) {
		return check(sub.getSuperclass().equals(sup), sub.getName() + 
				" should be a direct subclass of " + sup.getName());
	}

	private static class AccessChecker {
		Set<String> pubM = new HashSet<>();
		Set<String> proM = new HashSet<>();
		Set<String> defM = new HashSet<>();

		Set<String> pubF = new HashSet<>();
		Set<String> proF = new HashSet<>();
		Set<String> defF = new HashSet<>();

		Set<String> pub, pro, def;
		Class<?> c;

		public AccessChecker(Class<?> c) {
			this.c = c;
		}

		public AccessChecker pM(String name) {
			pubM.add(name);
			return this;
		}

		public AccessChecker tM(String name) {
			proM.add(name);
			return this;
		}

		public AccessChecker dM(String name) {
			defM.add(name);
			return this;
		}

		public AccessChecker pF(String name) {
			pubF.add(name);
			return this;
		}

		public AccessChecker tF(String name) {
			proF.add(name);
			return this;
		}

		public AccessChecker dF(String name) {
			defF.add(name);
			return this;
		}

		private void checkAccess(String name, int m) {
			String pName = c.getName() + "." + name;
			if (Modifier.isPrivate(m))
				return;
			if (Modifier.isProtected(m))
				check(pro.contains(name), pName
						+ " should not be protected");
			else if (Modifier.isPublic(m))
				check(pub.contains(name),
						pName + " should not be public");
			else
				check(def.contains(name), pName
						+ " should not be package-private (default)");
		}

		public void checkAll() {
			pub = pubM;
			def = defM;
			pro = proM;
			for (Method m : c.getDeclaredMethods())
				checkAccess(m.getName(), m.getModifiers());
			pub = pubF;
			def = defF;
			pro = proF;
			for (Field f : c.getDeclaredFields())
				checkAccess(f.getName(), f.getModifiers());
		}

	}

	private static void setInput(String str) {
		InputStream inputStream = new ByteArrayInputStream(
				str.getBytes(Charset.forName("UTF-8")));
		System.setIn(inputStream);
	}

	private static void printAllGood() {
		String thisClass = MethodHandles.lookup()
				.lookupClass().getName();

		if (allGood)
			System.out.println(thisClass + " Test passed!");
		else {
			System.err.println(
					"-------------------------------------");
			System.err.println(thisClass
					+ " Tests failed, see above.");
		}
	}

	
    private static PrintStream stdout = System.out;

	private static void ignoreOutput() {
		System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {}
        }));
	}
	
	private static void restoreOutput() {
		System.setOut(stdout);
	}	
	
// -------------------------------------------------------------------------------
// Testing tasks (question 2)

	private static boolean fulfills(int[] a, int task1,
			int task2) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == task1)
				return false;
			if (a[i] == task2)
				return true;
		}
		return false;
	}

	private static void checkDep(int[] a, int task1,
			int task2, String msg) {
		check(fulfills(a, task1, task2),
				msg + ": Answer " + Arrays.toString(a)
						+ " does not respect: " + task1
						+ " dependes on " + task2);
	}

	private static void testTasks() {
		new AccessChecker(Tasks.class).pM("dependsOn")
				.pM("order").checkAll();
		Tasks t = new Tasks(6);
		t.dependsOn(3, 2);
		t.dependsOn(0, 3);
		t.dependsOn(2, 5);
		t.dependsOn(4, 5);
		t.dependsOn(2, 4);

		check(!t.dependsOn(7, 4), "Tasks.dependsOn - not handling illegal output well");
		
		int[] order = t.order();
		System.out.println(Arrays.toString(order));
		checkDep(order, 3, 2, "Tasks(1)");
		checkDep(order, 0, 3, "Tasks(2)");
		checkDep(order, 2, 5, "Tasks(3)");
		checkDep(order, 4, 5, "Tasks(4)");
	}

	private static boolean fulfills(String[] a,
			String task1, String task2) {
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals(task1))
				return false;
			if (a[i].equals(task2))
				return true;
		}
		return false;
	}

	private static void checkDep(String[] a, String task1,
			String task2, String msg) {
		check(fulfills(a, task1, task2),
				msg + ": Answer " + Arrays.toString(a)
						+ " does not respect: " + task1
						+ " dependes on " + task2);
	}

	private static void testNamedTasks() {
		new AccessChecker(NamedTasks.class).pM("dependsOn")
				.pM("nameOrder").checkAll();
		checkSuper(NamedTasks.class, Tasks.class);
		
		String[] names = { "zero", "one", "two", "three",
				"four", "five" };
		NamedTasks t = new NamedTasks(names);
		t.dependsOn("three", "two");
		t.dependsOn("one", "three");
		t.dependsOn("two", "five");
		t.dependsOn("four", "five");
		
		check(!t.dependsOn("four", "brrr"), 
				"NamedTasks.dependsOn - not handling illegal output well");


		String[] order = t.nameOrder();
		checkDep(order, "three", "two", "NamedTasks");
		checkDep(order, "one", "three", "NamedTasks");
		checkDep(order, "two", "five", "NamedTasks");
		checkDep(order, "four", "five", "NamedTasks");
	}
// -------------------------------------------------------------------------------
// main

	public static void main(String[] args) {
		ignoreOutput();
		
		testTasks();
		testNamedTasks();
		restoreOutput();
		if (args != null)
			printAllGood();
	}
}
