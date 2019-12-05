package circuits;

import java.lang.StringBuilder;

public abstract class Gate {
	protected Gate[] inGates;
	
	
	public Gate(Gate[] inGates) {
		this.inGates = inGates;
	}
	public boolean calc() throws CircuitException{
		boolean []inValues = null;
		if(inGates != null) {
			inValues= new boolean[inGates.length];
			for(int i=0; i <inGates.length; i++ ) {
				inValues[i] = inGates[i].calc();
			}
		}
		return func(inValues);
	}
	
	protected abstract boolean func(boolean[] inValues) throws CircuitException;
	
	public abstract String getName();
	
	public abstract Gate simplify();
	
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(getName());
		if(inGates != null) {
			res.append("[");
			for (Gate g: inGates) {
				res.append(g.toString() + ", ");
			}
			res.delete(res.length() -2, res.length());
			res.append("]");
		}
		return res.toString();
	}
	
}
