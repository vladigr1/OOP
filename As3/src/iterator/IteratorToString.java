package iterator;

import java.lang.StringBuilder;

public class IteratorToString {

		public static String toString(Iterator it) {
			StringBuilder ret = new StringBuilder();
			ret.append("[");
			while(it.hasNext()) 
				ret.append(it.next() + " ");
			
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]");
			return ret.toString();
		}
}
