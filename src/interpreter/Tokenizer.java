package interpreter;

public class Tokenizer {
	
	private int nextTokenIndex;
	
	private Token[] tokens;
	
	public Tokenizer(Token[] tokens) {
		nextTokenIndex = 0;
		this.tokens = tokens;
		
	}
	
	//Retorna el siguiente token sacandolo de la lista.
	public Token nextToken() {
		Token nextToken = tokens[nextTokenIndex++];
		return nextToken;
	}
	
	public Token previousToken() {
		Token previousToken = tokens[--nextTokenIndex];
		return previousToken;
	}
	
	public String nextTokenValue() { 
		return nextToken().value();
	}
	
	
}
