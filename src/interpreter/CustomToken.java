package interpreter;

public class CustomToken {
	private Token token;
	private String text;
	
	public CustomToken(Token token, String text) {
		this.token = token;
		this.text = text;
	}

	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
}
