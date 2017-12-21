package interpreter;

public class Tokenizer {
	
	private int currentTokenIndex;
	
	private Token[] tokens;
	
	public Tokenizer(Token[] tokens) {
		currentTokenIndex = -1;
		this.tokens = tokens;
		
	}
	
	//Retorna el siguiente token sacándolo de la lista.
	public Token nextToken() {
		return tokens[++currentTokenIndex];
	}
	
	public Token previousToken() {
		return tokens[--currentTokenIndex];
	}
	
	public Token currentToken() {
		return tokens[currentTokenIndex];
	}
	
	public Token lookAhead(int n) {
		return tokens [currentTokenIndex + n];
	}
	
	public boolean lookAheadAllowed(int n) {
		return currentTokenIndex + n  < tokens.length;
	}
	
	public Token lookBehind(int n) {
		return tokens [currentTokenIndex - n];
	}
	
	public boolean lookBehindAllowed(int n) {
		return currentTokenIndex - n  >= 0;
	}
	
	public boolean hasNextToken() {
		return currentTokenIndex + 1 < tokens.length; 
	}
	
	public boolean hasPreviousToken() {
		return currentTokenIndex - 1 >= 0; 
	}
	
	public int currentIndex() {
		return currentTokenIndex;
	}
	
	public void setCurrentIndex(int v) {
		this.currentTokenIndex = v;
	}
	
	
	
	
}
