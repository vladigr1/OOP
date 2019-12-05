package circuits;

import java.util.Arrays;

public class OrGate extends Gate {

	public OrGate(Gate[] inGates) {
		super(inGates);
	}

	@Override
	protected boolean func(boolean[] inValues) throws CircuitException {
		for(Gate g: inGates) {
			if(g.calc() == true)return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "OR";
	}

	@Override
	public Gate simplify() {	//because in the end it will create new one we can use the inGates[]
		int i=0;
		Gate []newInGates = new Gate[inGates.length];
		for(Gate g: inGates) {
			Gate gSimplify  = g.simplify();
			if( gSimplify == TrueGate.instance())return  TrueGate.instance();
			if( gSimplify != FalseGate.instance())newInGates[i++] = gSimplify;
		}
		if(i == 0)return FalseGate.instance();
		if(i == 1)return newInGates[0];
		newInGates= Arrays.copyOf(newInGates, i);
		return new OrGate(newInGates);
	}

}
