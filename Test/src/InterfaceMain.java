import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterfaceMain{
	
	public class imTest implements interfaceTest{

	}
	public static void main(String[] args) {
		new InterfaceMain().run();
		Integer[] r = new Integer[4];
	}
	public void run() {
		System.out.println("_"+ interfaceTest.a);
		new imTest().f();
		List<Integer> l= this.<Integer>myL(3); 
		for(Integer k : l) {
			System.out.println(k);
		}
	}
	
	public <E> List<E> myL(E e) {
		List<E> l = new ArrayList<E>();
		l.add(e);
		l.add(e);
		l.add(e);
		return l;
	}
}