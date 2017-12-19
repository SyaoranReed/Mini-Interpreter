package interpreter;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		Reader reader = new Reader("/Users/Gabriel/Eclipse/Java/Mini-Interpreter/testCode/fibonaci.rpql");
		Lexer lexer = new Lexer(reader.getBufferedReader());
		ArrayList<String> tokens = new ArrayList<String>();
		while (true) {
			//Busca la sgte expresion regular
			String token = lexer.yylex();
			if (token == null) {
				break;
			}
			tokens.add(token);
		}
		String[] tokensArray = new String[tokens.size()];
		tokens.toArray(tokensArray);	
		Tokenizer tokenizer = new Tokenizer(tokensArray);
		Parser parser = new Parser(tokenizer);
	}

}
