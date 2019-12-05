import java.util.Comparator;

public abstract  class AbstractTest<T extends Runnable> implements Comparator<T>{

	@Override
	public abstract int compare(T o1, T o2);
	
	public <E extends T> T test(E e){
		e.run();
		return e;
	}
	
	public class subi<E>{
	}
}
