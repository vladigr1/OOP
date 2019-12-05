package tree;
import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class TreeTest {
	public static boolean allGood = true;

	private static boolean check(boolean cond, String message) {
		if (!cond) {
			System.err.println("Problem: " + message);
			allGood = false;
			return false;
		}
		return true;
	}

	private static boolean checkEq(Object a, Object b, String message) {
		return check(a.equals(b), String.format("%s: Expected \"%s\", but got \"%s\"",
				message, b, a));
	}

	private static boolean checkEqStr(Object a, Object b, String message) {
		return checkEq(a.toString(), b.toString(), message);
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
				check(pro.contains(name), pName + " should not be protected");
			else if (Modifier.isPublic(m))
				check(pub.contains(name), pName + " should not be public");
			else 
				check(def.contains(name), pName + " should not be package-private (default)");		
		}

		public void checkAll() {
			pub = pubM; def = defM; pro = proM;
			for (Method m : c.getDeclaredMethods())
				checkAccess(m.getName(), m.getModifiers());
			pub = pubF; def = defF; pro = proF;
			for (Field f : c.getDeclaredFields())  
				checkAccess(f.getName(), f.getModifiers());
		}
	
	}
		
	private static void setInput(String str) {
		InputStream inputStream = new ByteArrayInputStream(str.getBytes(Charset.forName("UTF-8")));
		System.setIn(inputStream); 
	}	
	
	private static void printAllGood() {
		String thisClass = MethodHandles.lookup().lookupClass().getName();

		if (allGood)
			System.err.println(thisClass  + " Test passed!");
		else {
			System.err.println("-------------------------------------");
			System.err.println(thisClass + " Tests failed, see above.");
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

	
//-----------------------------------------------------------------------------------------	
// Testing tree (question 1)	
	
	private static void testNode() {
		new AccessChecker(Node.class).pM("add").pM("num").checkAll();
		
		Node n = new Node();
		n.add("a");
		checkEq(n.num("a"), 1, "Node(1)");
		n.add("abc");
		checkEq(n.num("abc"), 1, "Node(2)");
		checkEq(n.num("a"), 1, "Node(3)");
		checkEq(n.num("b"), 0, "Node(4)");
		n.add("abc");
		checkEq(n.num("abc"), 2, "Node(5)");	
		
		Node n2 = new Node();
		n2.add("a");
		checkEq(n2.num("a"), 1, "Node(6)");
	}
	
	private static void testReversedWords() {
		new AccessChecker(ReversedWords.class).pM("checkReversed").checkAll();
		
		setInput("evil stressed star live raw pupils slipup where raw war rats live madam X");
        checkEq(ReversedWords.checkReversed(), 5, "ReversedWords");
	}
	
	
	
// -------------------------------------------------------------------------------
// main
	
	public static void main(String[] args) {
		ignoreOutput();

		testNode();
		testReversedWords();
		
		restoreOutput();
		if (args != null) 
			printAllGood();
	}
}
