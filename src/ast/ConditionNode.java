package ast;

import interpreter.Token;

public class ConditionNode {
	ValueNode value1;
	ValueNode value2;
	Token operator;
	
	public ConditionNode(ValueNode value1, ValueNode value2, Token operator) {
		this.value1 = value1;
		this.value2 = value2;
		this.operator = operator;
	}

	public boolean execute() {
		int comp = value1.execute().compareTo(value2.execute());
		switch(operator.value) {
			case "==": return comp == 0;
			case "!=": return comp != 0;
			case "<=": return comp <= 0;
			case ">=": return comp >= 0;
			case "<": return comp < 0;
			case ">": return comp > 0;
		}
		
		return false;
	}
	
}
