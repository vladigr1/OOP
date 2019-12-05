package images;

public class RGB {

	public static final RGB BLACK = new RGB(0,0,0);
	public static final RGB WHITE = new RGB(1,1,1);
	public static final RGB RED = new RGB(1,0,0);
	public static final RGB GREEN = new RGB(0,1,0);
	public static final RGB BLUE = new RGB(0,0,1);    
	
	private double red, green, blue;
	

	public RGB(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public RGB(double grey) {
		red = green = blue = grey;
	}

	public double getRed() {
		return red;
	}

	public double getGreen() {
		return green;
	}

	public double getBlue() {
		return blue;
	}

	public RGB invert() {
		return new RGB(1 - red, 1 - green, 1 - blue);
	}

	public RGB filter(RGB filter) {
		return new RGB(filter.red * red, filter.green * green, filter.blue * blue);
	}

	public static RGB superpose(RGB rgb1, RGB rgb2) {
		double nRed, nGreen, nBlue;
		nRed = (rgb1.red + rgb2.red < 1) ? rgb1.red + rgb2.red : 1;
		nGreen = (rgb1.green + rgb2.green < 1) ? rgb1.green + rgb2.green : 1;
		nBlue = (rgb1.blue + rgb2.blue < 1) ? rgb1.blue + rgb2.blue : 1;
		return new RGB(nRed, nGreen, nBlue);
	}
	
	private static double setNColor(double c1,double c2,double alpha) {
		double ret =alpha*c1 + (1-alpha) * c2;
		if(ret >1)return 1;
		if(ret <0)return 0;
		return ret;
	}
	
	public static RGB mix(RGB rgb1, RGB rgb2, double alpha) {
		double nRed, nGreen, nBlue;
		nRed = setNColor(rgb1.red,rgb2.red,alpha);
		nGreen = setNColor(rgb1.green,rgb2.green,alpha);
		nBlue = setNColor(rgb1.blue,rgb2.blue,alpha);
		return new RGB(nRed, nGreen, nBlue);
	}
	public String toString() {
		return String.format("<%.4f, %.4f, %.4f>",red,green,blue);
	}
}
