package ast;

import interpreter.Token;

public class ConditionNode {
	ValueNode value1;
	ValueNode value2;
	Token operator;
	
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
