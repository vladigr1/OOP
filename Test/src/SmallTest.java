
public class SmallTest {
	public static void main(String[] args) {
		String str1 = "abc" , str2;
		System.out.println(str1.hashCode());
		str2 = str1;
		System.out.println(str1 + " " + str1.hashCode() + " " + str2 + str2.hashCode() );
		str1 = str1 + "a";
		System.out.println(str1 + " " + str1.hashCode() + " " + str2 + str2.hashCode() );
	}
	
	static class tester{
		
	}
}

