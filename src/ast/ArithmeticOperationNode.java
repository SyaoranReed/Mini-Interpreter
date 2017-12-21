package ast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

import interpreter.Interpreter;


public class ArithmeticOperationNode implements ValueNode {

	private ArrayList<String> postfix;

	public ArithmeticOperationNode(ArrayList<String> postfix) {
		this.postfix = postfix;
	}

	@Override
	public BigInteger execute() {
		String regEx = Pattern.quote("+") + "|" + Pattern.quote("-") + "|" + Pattern.quote("*") + "|"
				+ Pattern.quote("/") + "|" + Pattern.quote("%");
		int i = 0;
		Stack<BigInteger> result = new Stack<>();
		while (i < postfix.size()) {
			String op = postfix.get(i);
			if (op.matches(regEx)) {
				BigInteger lastOp = result.pop();
				BigInteger secondToLastOp = result.pop();
				switch (op) {
					case "+":
						result.push(secondToLastOp.add(lastOp));
						break;
					case "-":
						result.push(secondToLastOp.subtract(lastOp));
						break;
					case "*":
						result.push(secondToLastOp.multiply(lastOp));
						break;
					case "/":
						result.push(secondToLastOp.divide(lastOp));
						break;
					case "%":
						result.push(secondToLastOp.mod(lastOp));
						break;
				}
			}
			else {
				if (op.startsWith("$")) {
					result.push(new VariableNode(op).execute());
				}
				else result.push(new BigInteger(op));
			}
			i++;
		}
		return result.pop();
	}

}
