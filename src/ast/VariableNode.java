package ast;



import java.math.BigInteger;

import interpreter.Interpreter;
import interpreter.Token;

public class VariableNode implements ValueNode {
	Token variable;
	
	
	public VariableNode(Token var) {
		this.variable = var;
	}

	@Override
	public BigInteger execute() {
		
		if(Interpreter.variableMap.get(variable.value) == null) {
			sendNotInitializedVariableErrorMessage();
			System.exit(0);
		}
		
		return Interpreter.variableMap.get(variable.value);
		
		
	}

	private void sendNotInitializedVariableErrorMessage() {
		System.out.println("Not initialized variable  " + variable.value + " in: line " + variable.line + "   column: " + variable.column);
		
	}
	
	
}
