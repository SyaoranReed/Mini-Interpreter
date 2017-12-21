package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Pattern;

public class ShuntingYard {
	private Stack<String> stack;
	private ArrayList<String> result;

	private HashMap<String, Integer> precedence;

	public ShuntingYard() {
		this.stack = new Stack<String>();
		this.result = new ArrayList<String>();
		this.precedence = new HashMap<String, Integer>();
		precedence.put("+", 1);
		precedence.put("-", 1);
		precedence.put("*", 2);
		precedence.put("/", 2);
		precedence.put("%", 2);
		precedence.put("n", 3);
	}

	public void add(Token token) {
		algorithm(token.value);
	}

	private void algorithm(String value) {
		if (stack.isEmpty()) {
			stack.push(value);
			return;
		}
		if (getPrecedence(value) <= getPrecedence(stack.peek())) {
			while (getPrecedence(value) <= getPrecedence(stack.peek())) {
				result.add(stack.pop());
				if (stack.isEmpty()) break; 
			}
			stack.push(value);
		}
		else {
			stack.push(value);
		}
	}

	private int getPrecedence(String val1) {
		String regEx = Pattern.quote("+") + "|" + Pattern.quote("-") + "|" + Pattern.quote("*") + "|"
				+ Pattern.quote("/") + "|" + Pattern.quote("%");
		if (val1.matches(regEx)) {
			return precedence.get(val1);
		}
		return precedence.get("n");

	}

	public ArrayList<String> getPostfix() {
		while (!stack.isEmpty()) {
			result.add(stack.pop());
		}
		return result;
	}
	
	//Devuelve cuandos elementos se han agregado hasta ahora
	public int sizeOpSoFar() {
		return result.size();
	}
	
	//Para probar que esta bien implementado
	public static void main(String[] args) {
		ShuntingYard shuntingYard = new ShuntingYard();
		shuntingYard.algorithm("5");
		shuntingYard.algorithm("+");
		shuntingYard.algorithm("6");
		shuntingYard.algorithm("*");
		shuntingYard.algorithm("7");
		shuntingYard.algorithm("+");
		shuntingYard.algorithm("8");
		for (String string : shuntingYard.getPostfix()) {
			System.out.print(string + " ");
		}
	}
	
}
