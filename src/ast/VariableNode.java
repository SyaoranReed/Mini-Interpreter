package ast;

import java.math.BigInteger;

import interpreter.Interpreter;

public class VariableNode implements ValueNode {
	String id;

	@Override
	public BigInteger execute() {
		
		if(!Interpreter.variableMap.containsKey(id)) {
			sendNotInitializedVariableErrorMessage();
			System.exit(0);
		}
		
		return Interpreter.variableMap.get(id);
		
		
	}

	private void sendNotInitializedVariableErrorMessage() {
		System.out.println("La variable " + id + "no ha sido inicializada");
		
	}
	
	
}
