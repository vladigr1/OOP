package images;



public class Circle extends BaseImage {
	
	private int centerX,centerY,radius;
	private RGB center, outside;
	
	public Circle(int width, int height, int centerX, int centerY, int radius, RGB center, RGB outside) {
		super(width,height);
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.center = center;
		this.outside = outside;
	}
	
	public Circle(int size, int radius, RGB center, RGB outside) {
		super(size,size);
		this.centerX = size/2;
		this.centerY = size/2;
		this.radius = radius;
		this.center = center;
		this.outside = outside;
	}
	
	public RGB get(int x, int y) {
		double distance = Math.pow(x - centerX, 2)  + Math.pow(y - centerY, 2);
		distance= Math.sqrt(distance);
		if(distance > radius) return outside;
		return RGB.mix(outside, center, distance/radius);
	}
}
