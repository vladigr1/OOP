package images;

public abstract class BinaryImageDecorator implements Image {
	
	protected Image base1,base2;
	
	public BinaryImageDecorator(Image base1, Image base2) {
		this.base1 = base1;
		this.base2 = base2;
	}
	@Override
	public int getWidth() {
		int maxWidth = base1.getWidth() > base2.getWidth() ? base1.getWidth() : base2.getWidth();
		return maxWidth;
	}

	@Override
	public int getHeight() {
		int maxHeight = base1.getHeight() > base2.getHeight() ? base1.getHeight() : base2.getHeight();
		return maxHeight;
	}
	
	protected abstract RGB getCollide(int x, int y);
	
	@Override
	public RGB get(int x, int y) {
		int score =0;
		if(base1.getHeight() > y  && base1.getWidth() > x) {
			score += 1;
		}
		if(base2.getHeight() > y  && base2.getWidth() > x) {
			score += 2;
		}
		switch(score) {//Don't need break unreachable code
		case 1:
			return base1.get(x, y);
		case 2:
			return base2.get(x, y);
		case 3:
			return getCollide(x, y);
		default:
			return RGB.BLACK;
		}
	}
}
