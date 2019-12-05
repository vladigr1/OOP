package circuits;

public class FalseGate extends Gate {

	private static FalseGate singleton = null;
	
	private FalseGate() {
		super(null);
	}
	
	public static Gate instance() {
		if(singleton == null) {
			singleton = new FalseGate();
		}
		return singleton;
	}
	
	@Override
	protected boolean func(boolean[] inValues) throws CircuitException {
		return false;
	}

	@Override
	public String getName() {
		return "F";
	}

	@Override
	public Gate simplify() {
		return this;
	}
}
