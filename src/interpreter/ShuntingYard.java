package interpreter;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Pattern;



public class ShuntingYard {
	private Stack<Token> stack;
	private ArrayList<Token> result;

	private HashMap<String, Integer> precedence;

	public ShuntingYard() {
		this.stack = new Stack<Token>();
		this.result = new ArrayList<Token>();
		this.precedence = new HashMap<String, Integer>();
		precedence.put("+", 1);
		precedence.put("-", 1);
		precedence.put("*", 2);
		precedence.put("/", 2);
		precedence.put("%", 2);
		precedence.put("n", 3);
	}

	public void add(Token token) {
		algorithm(token);
	}

	private void algorithm(Token token) {
		if (stack.isEmpty()) {
			stack.push(token);
			return;
		}
		if (getPrecedence(token) <= getPrecedence(stack.peek())) {
			while (getPrecedence(token) <= getPrecedence(stack.peek())) {
				result.add(stack.pop());
				if (stack.isEmpty()) break; 
			}
			stack.push(token);
		}
		else {
			stack.push(token);
		}
	}

	private int getPrecedence(Token token) {
		String regEx = Pattern.quote("+") + "|" + Pattern.quote("-") + "|" + Pattern.quote("*") + "|"
				+ Pattern.quote("/") + "|" + Pattern.quote("%");
		if (token.value.matches(regEx)) {
			return precedence.get(token.value);
		}
		return precedence.get("n");

	}

	public ArrayList<Token> getPostfix() {
		while (!stack.isEmpty()) {
			result.add(stack.pop());
		}
		
		return result;
	}
	
	public Token getTokenPostfix(int i) {
		return result.get(i);
	}
	
	//Devuelve cuandos elementos se han agregado hasta ahora
	public int sizeOpSoFar() {
		return result.size();
	}
	
	
	
}
