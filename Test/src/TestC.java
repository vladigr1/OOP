
public class TestC {
	static void overloadedFunc(Object obj) {
		System.out.println("3");
	}
	
	static void overloadedFunc(Two b) {
		System.out.println("2");
	}

	static void overloadedFunc(One a) {
		System.out.println("1");
	}

	public static class One {
	}

	public static class Two extends One {
	}

	public static class Three extends Two{
	}
	

	public static void main(String[] args) {
		Three a = new Three();
		overloadedFunc(a);
	}
}
