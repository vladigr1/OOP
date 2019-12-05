import java.util.Scanner;

public class TestScanner {
	public static void main (String args[]) {
		Scanner test = new Scanner(System.in);
		while(test.hasNext()) {
			System.out.println(test.next());
		}
		test.close();
	}
}
