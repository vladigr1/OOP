import java.lang.Integer;

public class WrapPrim{
	
	public static void main(String[] argv) {
		int a =1;
		Integer p_a= new Integer(a);
		System.out.format("a =  %d | p_a = %d%n",a, p_a.intValue() );
		a = 2;
		System.out.format("a =  %d | p_a = %d%n",a, p_a.intValue() );
	}
}