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
	
	@Override
	public String toString() {
		String string;
		if(type == TokenType.ARISMETIC) return "operador aritmetico ('+', '-', '*', '/', '%')";
		else if(type == TokenType.LOGIC) return "operador lógico ('==', '!=', '<=', '>=', '<', '>') "; 
		else return "'" + value + "'";
	}
	
	
}
