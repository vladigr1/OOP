package images;

public class Gradient extends BaseImage {
	
	private RGB start,end;
	
	public Gradient(int width, int height, RGB start, RGB end) {
		super(width, height);
		this.start = start;
		this.end = end;
	}
	
	@Override
	public RGB get(int x, int y) {
		return RGB.mix(end,start,((double) x)/width);
	}
	
	public static void main(String[] args) {

		Image i = new Gradient(200, 100, RGB.RED, 
				new RGB(1, 1, 0));
		Displayer.display(i);

	}
}
