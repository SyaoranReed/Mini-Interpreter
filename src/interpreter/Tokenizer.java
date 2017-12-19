package interpreter;

public class Tokenizer {
	
	private int currentTokenIndex;
	
	private String[] tokens;
	
	public Tokenizer(String[] tokens) {
		currentTokenIndex = -1;
		this.tokens = tokens;
		
	}
	
	//Retorna el siguiente token sacándolo de la lista.
	public String nextToken() {
		return tokens[++currentTokenIndex];
	}
	
	public String previousToken() {
		return tokens[--currentTokenIndex];
	}
	
	public String currentToken() {
		return tokens[currentTokenIndex];
	}
	
	public String lookAhead(int n) {
		return tokens [currentTokenIndex + n];
	}
	
	public boolean lookAheadAllowed(int n) {
		return currentTokenIndex + n  < tokens.length;
	}
	
	
	
	public boolean hasNextToken() {
		return currentTokenIndex + 1 >= 0; 
	}
	
	public boolean hasPreviousToken() {
		return currentTokenIndex - 1 >= 0; 
	}
	
	
	
	
	
}
