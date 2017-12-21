package interpreter;

public enum TokenType {
	WHILE("while"), IF("if"), WRITE("write"), READ("read"), DO("do"), WEND("wend"), SEMICOLON(";"), 
	THEN("then"), ELSE("else"), ENDIF("endif"), ASSIGN("="), ARITHMETIC_OPERATOR("arithmetic_operator"),
	LOGICAL_OPERATOR("logical_operator" ), INT("natural_number"), VAR("$id"), ERROR("invalid token"), L_PARENTH("("), R_PARENTH(")"), EOF("eof");
	
	private String token;
	
	private TokenType(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return this.token;
	}
	
	
}
