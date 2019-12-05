package circuits;

import java.util.Arrays;

public class AndGate extends Gate{

	public AndGate(Gate[] inGates) {
		super(inGates);
	}

	@Override
	protected boolean func(boolean[] inValues) throws CircuitException {
		for(Gate g: inGates) {
			if(g.calc() == false)return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "AND";
	}
	
	public Gate simplify() {	//because in the end it will create new one we can use the inGates[]
		int i=0;
		Gate []newInGates = new Gate[inGates.length];
		for(Gate g: inGates) {
			Gate gSimplify = g.simplify();
			if( gSimplify == FalseGate.instance())return  FalseGate.instance();
			if( gSimplify != TrueGate.instance())newInGates[i++] = gSimplify;
		}
		if(i == 0)return TrueGate.instance();
		if(i == 1)return newInGates[0];
		newInGates = Arrays.copyOf(inGates, i);
		return new AndGate(newInGates);
	}
}
