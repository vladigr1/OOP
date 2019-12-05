import java.util.Arrays;

public class MemAllocTest {
	public static void main(String[] argv) {
		int[] arr1 = {1,2,3};
		//int [] arr2 = arr1 // will  put the refrence to arr2 and we want to may seprate obj
		int[] arr2 = new int[3];
		arr2 = Arrays.copyOfRange(arr1, 0, 3);
		arr1[1] = 0;
		String a = "abc";
		String b = a;
		a= a +"de";
		a += 'f';
		System.out.println(a);
		System.out.println(b);
		System.out.println(Arrays.toString(arr1));
		System.out.println(Arrays.toString(arr2));
	}
}
