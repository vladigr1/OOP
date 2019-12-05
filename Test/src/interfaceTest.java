
public interface interfaceTest {
	int a = 5;	//Defaultly public static
	public default void f() {
		System.out.println(a);
	}
}