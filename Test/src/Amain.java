public class Amain {

	static void main(String[] args) {
		B b = new B();
		Number t = b.f(b);
		System.out.println(b.getClass().getName() + " " + t.getClass().getName());
	}
}
