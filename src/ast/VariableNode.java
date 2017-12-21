package ast;

import java.math.BigInteger;

import interpreter.Interpreter;

public class VariableNode implements ValueNode {
	String id;

	@Override
	public BigInteger execute() {
		
		return Interpreter.variableMap.get(id);
		
	}
	
	
}
