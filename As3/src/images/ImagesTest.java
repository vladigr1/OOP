package images;

import main.Tester;
import main.Tester.OneTest;

public class ImagesTest {
	private Tester t;

	private static RGB g1 = new RGB(0.2, 0.7, 0.111);
	private static RGB g2 = new RGB(0.9, 0.3, 0.75);
	private static RGB g3 = new RGB(0.3, 0.6, 0.21);
	private static Image circle = new Circle(20, 10, 5, 8, 10, g1, g2);
	private static Image grad = new Gradient(10, 20, g3, g2);

	private static boolean smallDiff(double x, double y) {
		return Math.abs(x - y) < 0.001;
	}

	private abstract class OneTestImages extends OneTest {

		public OneTestImages(Tester tester, Class<?> currentClass, int minus,
				String testDesc) {
			tester.super(currentClass, minus, testDesc);
		}

		void checkEqRGB(RGB rgb1, RGB rgb2, String message) {
			check(smallDiff(rgb1.getRed(), rgb2.getRed())
					&& smallDiff(rgb1.getBlue(), rgb2.getBlue())
					&& smallDiff(rgb1.getGreen(), rgb2.getGreen()),
					String.format("%sExpected %s but got %s", 
							message == null || message.equals("") ? "" : message + ": ", 
							rgb2, rgb1));
		}
	}

	private void testRGB() {
		Class<?> c = RGB.class;
		t.new AccessChecker(c).pM("getRed").pM("getBlue").pM("getGreen")
				.pM("invert").pM("filter").pM("superpose").pM("mix")
				.pM("toString").pF("BLACK").pF("WHITE").pF("RED").pF("BLUE")
				.pF("GREEN").checkAll();

		new OneTestImages(t, c, 0, "Testing all RGB's methods") {
			@Override
			public void test() {
				checkEqDouble(g1.getRed(), 0.2, "getRed");
				checkEqDouble(g1.getGreen(), 0.7, "getGreen");
				checkEqDouble(g1.getBlue(), 0.111, "getBlue");
				checkEqStr(g1, "<0.2000, 0.7000, 0.1110>", "toString");

				checkEqRGB(new RGB(0.2), new RGB(0.2, 0.2, 0.2),
						"grey constructor");
				checkEqRGB(g1.invert(), new RGB(0.8, 0.3, 1 - 0.111), "invert");
				checkEqRGB(g1, new RGB(0.2, 0.7, 0.111),
						"invert should not change original");

				checkEqRGB(g1.filter(g2), new RGB(0.18, 0.21, 0.08325),
						"filter");
				checkEqRGB(RGB.mix(g1, g2, 0.3), new RGB(0.69, 0.42, 0.5583),
						"mix");
				checkEqRGB(RGB.BLACK, new RGB(0, 0, 0), "BLACK");
				checkEqRGB(RGB.WHITE, new RGB(1, 1, 1), "WHITE");
				checkEqRGB(RGB.RED, new RGB(1, 0, 0), "RED");
				checkEqRGB(RGB.GREEN, new RGB(0, 1, 0), "GREEN");
				checkEqRGB(RGB.BLUE, new RGB(0, 0, 1), "BLUE");
			}
		};
	}

	private void testBaseImage() {
		t.checkImplements(BaseImage.class, Image.class);
	}

	private void testGradient() {
		Class<?> c = Gradient.class;
		t.checkSuper(c, BaseImage.class);
		t.new AccessChecker(c).pM("get").checkAll();
		new OneTestImages(t, c, 0, null) {
			@Override
			public void test() {
				Image i = new Gradient(20, 10, g1, g2);
				checkEq(i.getWidth(), 20, "getWidth");
				checkEq(i.getHeight(), 10, "getHeight");
				checkEqRGB(i.get(3, 2), new RGB(0.305, 0.64, 0.20685), "check1");
			}
		};
	}

	private void testCircle() {
		Class<?> c = Circle.class;
		t.checkSuper(c, BaseImage.class);
		t.new AccessChecker(c).pM("get").checkAll();
		new OneTestImages(t, c, 0, null) {
			@Override
			public void test() {
				checkEqRGB(circle.get(4, 7),
						new RGB(0.2998995, 0.643431, 0.201368), "inside circle");
				checkEqRGB(circle.get(19, 9), g2, "outside circle");
			}
		};
	}

	private void testImageDecorator() {
		t.checkImplements(ImageDecorator.class, Image.class);
	}

	private void testFilter() {
		Class<?> c = Filter.class;
		t.checkSuper(c, ImageDecorator.class);
		t.new AccessChecker(c).pM("get").checkAll();
		new OneTestImages(t, c, 0, null) {
			@Override
			public void test() {
				Image i = new Filter(circle, g3);
				checkEqRGB(i.get(2, 5), new RGB(0.149095, 0.318177, 0.080242),
						"check");
			}
		};
	}

	private void testInvert() {
		Class<?> c = Invert.class;
		t.checkSuper(c, ImageDecorator.class);
		t.new AccessChecker(c).pM("get").checkAll();
		new OneTestImages(t, c, 0, null) {
			@Override
			public void test() {
				Image i = new Invert(circle);
				checkEqRGB(i.get(2, 5), new RGB(0.503015, 0.469706, 0.617895),
						"check");
			}
		};
	}

	private void testTranspose() {
		Class<?> c = Transpose.class;
		t.new AccessChecker(c).pM("get").pM("getWidth").pM("getHeight")
				.checkAll();
		new OneTestImages(t, c, 0, null) {
			@Override
			public void test() {
				Image i = new Transpose(circle);
				checkEq(i.getWidth(), 10, "getWidth");
				checkEq(i.getHeight(), 20, "getHeight");
				checkEqRGB(i.get(2, 5), new RGB(0.620000, 0.460000, 0.494400),
						"check");
			}
		};
	}

	private void testBinaryImageDecorator() {
		Class<?> c = BinaryImageDecorator.class;
		t.checkImplements(c, Image.class);
	}

	private void testSuperpose() {
		Class<?> c = Superpose.class;
		t.checkSuper(c, BinaryImageDecorator.class);
		Image i = new Superpose(circle, grad);
		new OneTestImages(t, c, 0, null) {
			@Override
			public void test() {
				checkEq(i.getWidth(), 20, "getWidth");
				checkEq(i.getHeight(), 20, "getHeight");
				checkEqRGB(i.get(1, 1), new RGB(1.000000, 0.947510, 0.890178),
						"check1");
				checkEqRGB(i.get(18, 18), RGB.BLACK, "check2");
				checkEqRGB(i.get(18, 1), new RGB(0.900000, 0.300000, 0.750000),
						"check3");
				checkEqRGB(i.get(1, 18), new RGB(0.360000, 0.570000, 0.264000),
						"check4");
			}
		};
	}

	private void testMix() {
		Class<?> c = Mix.class;
		t.checkSuper(c, BinaryImageDecorator.class);
		new OneTestImages(t, c, 0, null) {
			@Override
			public void test() {
				Image i = new Mix(circle, grad, 0.3);
				checkEq(i.getWidth(), 20, "getWidth");
				checkEq(i.getHeight(), 20, "getHeight");
				checkEqRGB(i.get(1, 1), new RGB(0.481307, 0.512253, 0.372653),
						"check1");
				checkEqRGB(i.get(18, 18), RGB.BLACK, "check2");
				checkEqRGB(i.get(18, 1), new RGB(0.900000, 0.300000, 0.750000),
						"check3");
				checkEqRGB(i.get(1, 18), new RGB(0.360000, 0.570000, 0.264000),
						"check4");
			}
		};
	}

	private void testTwoColorImage() {
		Class<?> c = TwoColorImage.class;
		t.checkSuper(c, BaseImage.class);
		t.new AccessChecker(c).pM("get").checkAll();
		new OneTestImages(t, c, 0, null) {
			@Override
			public void test() {
				Image i = new TwoColorImage(10, 20, RGB.RED, RGB.GREEN,
						new TwoDFunc() {
							@Override
							public double f(double x, double y) {
								return Math.sin(x * Math.PI * 2)
										+ Math.cos(y * Math.PI * 2) + 2;
							}
						});
				checkEqRGB(i.get(4, 7), new RGB(0.000000, 1.000000, 0.000000),
						"check1");
				checkEqRGB(i.get(8, 11), new RGB(0.902113, 0.097887, 0.000000),
						"check2");
			}
		};
	}

	private void complexTest() {
		new OneTestImages(t, ImagesTest.class, 0, null) {
			@Override
			public void test() {
				Image i1 = new Gradient(500, 500, RGB.BLUE, RGB.BLACK);
				Image i2 = new Transpose(new Gradient(500, 500, RGB.RED, RGB.BLACK));
				Image i3 = new Mix(i1, i2, 0.5);
				Image i4 = new Circle(350, 150, new RGB(1, 1, 0), RGB.BLACK);
				Image i5 = new Circle(200, 100, new RGB(0, 0.5, 1), RGB.BLACK);
				Image i6 = new Circle(500, 200, RGB.WHITE, RGB.BLACK);
				Image i7 = new Superpose(i3, i4);
				Image i8 = new Superpose(i5, i6);
				Image i9 = new Superpose(i7, i8);

				checkEqRGB(i9.get(100, 100),
						new RGB(0.692893, 0.792893, 1.000000), "check1");
				checkEqRGB(i9.get(200, 400),
						new RGB(0.309431, 0.209431, 0.509431), "check2");
			}
		};
	}

	// -------------------------------------------------------------------------------
	// main

	public Tester actualTest() {
		t = new Tester();
		testRGB();
		testBaseImage();
		testGradient();
		testCircle();
		testImageDecorator();
		testFilter();
		testInvert();
		testTranspose();
		testBinaryImageDecorator();
		testSuperpose();
		testMix();
		testTwoColorImage();
		complexTest();
		return t;
	}

	public static Tester test() {
		return new ImagesTest().actualTest();
	}

	public static void main(String[] args) {
		Tester.forStudents = true;
		Tester.ignoreOutput();
		Tester t = test();
		Tester.restoreOutput();
		t.printAllGood("ImagesTest");
	}
}