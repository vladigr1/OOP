package tree;



public class Node {
	
	private int count; 
	private Node[] children;
	private static final int LAM = 26;
	
	public Node() {
		this.count =0;
		this.children = new Node[LAM];
	}
	
	public int num(String s) {
		int i;
		Node nextChild = this;
		for(i = 0; i < s.length();i++) {
			nextChild = nextChild.children[s.charAt(i) - 'a'];
			if(nextChild == null) return 0;
		}
		return nextChild.count;
	}

	private void addRecursive(String s,Node cur) {
		if(s.equals("")) {
			cur.count++;
			return;
		}
		Node newNode;
		int index = s.charAt(0) - 'a';
		if(cur.children[index] == null) {
			newNode = new Node();
			cur.children[index] = newNode;
		} else {
			newNode = cur.children[index];
		}
		String newString = s.substring(1);
		addRecursive(newString,newNode);
	}
	public void add(String s) {
		addRecursive(s, this);
	}
}
