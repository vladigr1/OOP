package circuits;

public class VarGate extends Gate {
	private String name;
	private Boolean val;
	
	public VarGate(String name) {
		super(null);
		this.name = "V" +  name;
		val = null;
	}
	
	@Override
	protected boolean func(boolean[] inValues) throws CircuitException {
		if(val == null)throw new CircuitException("didnt set " + this);
		return val;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setVal(boolean value) {
		val = new Boolean(value);
	}

	@Override
	public Gate simplify() {
		if(val == null) return this;
		if(val == true) return TrueGate.instance();
		return FalseGate.instance();
	}
}
