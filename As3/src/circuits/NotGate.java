package circuits;

public class NotGate extends Gate {

	public NotGate(Gate in) {
		super(new Gate[] {in} );
	}
	
	@Override
	protected boolean func(boolean[] inValues) throws CircuitException {
		
		return !(inValues[0]);
	}

	@Override
	public String getName() {
		return "NOT";
	}

	@Override
	public Gate simplify() {
		Gate simplfyGate = inGates[0].simplify();
		if(simplfyGate == TrueGate.instance())return TrueGate.instance();
		if(simplfyGate == FalseGate.instance())return FalseGate.instance();
		if (simplfyGate instanceof NotGate)return simplfyGate.simplify();
		return this;
	}

}
