package graph;

public class Place {
	
	private final int x,y,bound;
	private int role;	// 0 - wall 1 - start 2 - end 3-clear space
	
	public Place(int x, int y, int bound) {
		if(x >= bound  || y >= bound || x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		this.x = x;
		this.y = y;
		this.bound = bound;
		this.role =0;
	}
	
	public Place(int x, int y, int bound,int role)  {
		this(x,  y,  bound);
		this.role = role;
	}
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getRole() {
		return role;
	}

	@Override
	public boolean equals(Object other) {
		Place p;
		if(other instanceof Place) {
			p = (Place)other;
		}else {
			return false;
		}
		if(p.x == x && p.y == y) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return x * bound + y;		//unique number for each point
	}
	
	@Override
	public String toString() {
		switch(role) {
		case 1:
			return "S";
		case 2:
			return "E";
		case 3:
			return ".";	//maybe a case in graph that you may want to do tostring
		default:
			return "@";	//null or clear space
		}
	}
}
