package interpreter;

public class Token {
	public final TokenType type;
	public final String value;
	public final int line;
	public final int column;
	
	public Token(TokenType type, String value, int line, int column) {
		this.type = type;
		this.value = value;
		this.line = line;
		this.column = column;
	}
	
	public boolean isType(TokenType type) {
		return this.type == type;
	}
}