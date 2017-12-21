package ast;

import java.math.BigInteger;

import interpreter.Interpreter;

public class VariableNode implements ValueNode {
	String id;
	
	
	public VariableNode(String val) {
		this.id = val;
	}

	@Override
	public BigInteger execute() {
		
		if(!Interpreter.variableMap.containsKey(id)) {
			sendNotInitializedVariableErrorMessage();
			System.exit(0);
		}
		
		return Interpreter.variableMap.get(id);
		
		
	}

	private void sendNotInitializedVariableErrorMessage() {
		System.out.println("La variable " + id + " no ha sido inicializada");
		
	}
	
	
}
