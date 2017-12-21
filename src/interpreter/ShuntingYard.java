package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Pattern;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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

	public ArrayList<String> getPostfix() {
		while (!stack.isEmpty()) {
			result.add(stack.pop());
		}
		ArrayList<String> res = new ArrayList<>(); 
		for (Token token : result) {
			res.add(token.value);
		}
		return res;
	}
	
	public Token getTokenPostfix(int i) {
		return result.get(i);
	}
	
	//Devuelve cuandos elementos se han agregado hasta ahora
	public int sizeOpSoFar() {
		return result.size();
	}
	
	//Para probar que esta bien implementado
	public static void main(String[] args) {
		ShuntingYard shuntingYard = new ShuntingYard();
		shuntingYard.algorithm(new Token(TokenType.INT, "5", 0, 0));
		shuntingYard.algorithm(new Token(TokenType.ARITHMETIC_OPERATOR, "+", 0, 0));
		shuntingYard.algorithm(new Token(TokenType.INT, "6", 0, 0));
		shuntingYard.algorithm(new Token(TokenType.ARITHMETIC_OPERATOR, "*", 0, 0));
		shuntingYard.algorithm(new Token(TokenType.INT, "7", 0, 0));
		shuntingYard.algorithm(new Token(TokenType.ARITHMETIC_OPERATOR, "+", 0, 0));
		shuntingYard.algorithm(new Token(TokenType.INT, "8", 0, 0));
		for (String string : shuntingYard.getPostfix()) {
			System.out.print(string + " ");
		}
	}
	
}
