package circuits;

public class TrueGate extends Gate{
	private static TrueGate singleton = null;
	
	private TrueGate() {
		super(null);
	}
	
	public static Gate instance() {
		if(singleton == null) {
			singleton = new TrueGate();
		}
		return singleton;
	}
	
	@Override
	protected boolean func(boolean[] inValues) throws CircuitException {
		return true;
	}

	@Override
	public String getName() {
		return "T";
	}

	@Override
	public Gate simplify() {
		return this;
	}

}
