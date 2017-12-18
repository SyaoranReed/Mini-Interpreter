package interpreter;

public class Token {
	private TokenType tokenType;
	private String value;
	
	public Token(TokenType tokenType, String value) {
		this.tokenType = tokenType;
		this.value = value;
	}

	
	public TokenType type() {
		return tokenType;
	}

	
	public String value() {
		return this.value;
	}
}
