package interpreter;

public class Tokenizer {
	
	private int nextTokenIndex;
	
	private String[] tokens;
	
	public Tokenizer(String[] tokens) {
		nextTokenIndex = -1;
		this.tokens = tokens;
		
	}
	
	//Retorna el siguiente token sacandolo de la lista.
	public String nextToken() {
		return tokens[--nextTokenIndex];
	}
	
	public String previousToken() {
		return tokens[--nextTokenIndex];
	}
	
	
	
	
}
