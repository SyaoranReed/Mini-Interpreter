package ast;

import interpreter.Interpreter;

public class AssignmentNode implements ASTInstructionNode {

	String variableID;
	ValueNode value; //El valor que se le va a asignar a la variable.
	
	public AssignmentNode(String variableID, ValueNode value) {
		this.variableID = variableID;
		this.value = value;
	}

	@Override
	public void execute() {
		Interpreter.variableMap.put(variableID, value.execute());
		
	}

}
