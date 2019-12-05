


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import tasks.Tasks;
import tree.ReversedWords;
import game.TicTacToe;
import game.FourInARow;;

public class MyTest {
	
	public static void main(String[] args) {
		taskTest();
	}

	public static void taskTest() {
		// 4,5,1,2,3,0
				Tasks t = new Tasks(6);
				t.dependsOn(0, 1);
				t.dependsOn(0, 1);
				t.dependsOn(0, 2);
				t.dependsOn(0, 3);
				t.dependsOn(3, 1);
				t.dependsOn(3, 1);
				t.dependsOn(3, 2);
				t.dependsOn(2, 1);
				t.dependsOn(1, 5);
				int []arr = t.order();
				System.out.println(Arrays.toString(arr));
	}

}
