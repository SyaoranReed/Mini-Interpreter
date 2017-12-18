package interpreter;

import java.io.IOException;

public class PruebaLexer {
	public static void main(String[] args) throws IOException {
		Reader reader = new Reader("/Users/Gabriel/Eclipse/Java/Mini-Interpreter/fibonaci.rpql");
		Lexer lexer = new Lexer(reader.getBufferedReader());
		
		while (true) {
			//Busca la sgte expresion regular
			Token token = lexer.yylex();
			if (token == null) {
				break;
			}
			System.out.printf("%-11s => %s\n",token.type(), token.value());
		}
	}
}
