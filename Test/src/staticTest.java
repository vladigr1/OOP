import java.util.Arrays;
import java.util.List;

public class staticTest {
	//private  int[] testp;			//doesn't work
	private static int[] test;
	public static final int abc =5;
	public static void main(String[] args) {
		//test = new int[3];		//cannot make static reference dynamic 
		//test(3);					//Cant even with function
		//int i =3;
		Integer[] a = {1,2,3};
		test = new int[3];
		List<Integer> l = Arrays.<Integer>asList(a);
		for(Integer t: l) {
			System.out.println(t);
		}
		test[0] = 2;
		Arrays.sort(test);
		//stringTest(Integer.toString(i));
	}
	
	public static void test(int size) {
		test = new int[size];
	}
	
	public static void stringTest (String i) {
		System.out.println(i);
	}
}


// you must 