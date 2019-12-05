package images;

public class Superpose extends BinaryImageDecorator{
	public Superpose(Image base1, Image base2) {
		super(base1,base2);
	}

	@Override
	protected RGB getCollide(int x, int y) {
		return RGB.superpose(base1.get(x, y), base2.get(x, y));
	}

	

}
