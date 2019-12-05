package images;

public class Transpose extends ImageDecorator{

	public Transpose(Image base) {
		super(base);
	}
	
	@Override
	public int getWidth() {
		return base.getHeight();
	}

	@Override
	public int getHeight() {
		return base.getWidth();
	}
	@Override
	public RGB get(int x, int y) {
		return base.get(y, x);		//we swap in the getter but nut reset it
	}

}
