package images;

public class TwoColorImage extends BaseImage {
	
	private TwoDFunc func;
	private RGB zero,one;
	
	public TwoColorImage(int width, int height, RGB zero, RGB one, TwoDFunc func) {
		super(width, height);
		this.zero = zero;
		this.one = one;
		this.func = func;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public RGB get(int x, int y) {
		return RGB.mix(one, zero, func.f((double )x /width, (double )y/height) );
	}
	

}
