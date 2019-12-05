package tree;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ReversedWords {
	
	private static Scanner input;

	private static String reverseString(String s) {	//revse the sended string
		String ret = "";
		  for(int i = s.length() - 1; i >= 0; i--)
	        {
	            ret = ret + s.charAt(i);
	        }
		  return ret;
	}
	
	public static int checkReversed() {					
		String word = "X";					//in case the 
		int amount =0;
		Node tree = new Node();
		input = new Scanner(System.in);
		while(input.hasNext()) {	//in case you inputed a line
			word = input.next();
			if(word.compareTo("X") == 0)break;
			if(tree.num( reverseString(word) ) != 0)amount++;
			tree.add(word);
		}
		input.close();
		return amount;
	}
	
}
