package interpreter;

import java.io.IOException;
import java.math.BigInteger;

public class PruebaLexer {
	public static void main(String[] args) throws IOException {
		Reader reader = new Reader("/Users/Gabriel/Eclipse/Java/Mini-Interpreter/testCode/fibonaci.rpql");
		Lexer lexer = new Lexer(reader.getBufferedReader());
		//System.out.printf("%-8s | %-4s | %-6s \n","Token","line","column");
		while (true) {
			//Busca la sgte expresion regular
			Token token = lexer.yylex();
			if (token == null) {
				break;
			}
			//System.out.printf("%-8s | %-4s | %-6s \n",token.value,token.line,token.column);
			System.out.println(token.value + " "+ token.type);
		}
		
	}
}
