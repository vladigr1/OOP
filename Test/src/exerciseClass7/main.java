package exerciseClass7;

public class main {

	public static void main(String[] args) {
		MyMap<String, Integer> map = new MyMap<>();
		map.put("one", 1);
		map.put("three", 3);
		map.put("seven", 7);
		map.put("four", 4);
		System.out.println(map.get("seven"));

	}

}
