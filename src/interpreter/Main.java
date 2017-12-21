package interpreter;

import java.io.IOException;
import java.util.ArrayList;

import ast.BlockNode;

public class Main {

	public static void main(String[] args) throws IOException {
		Reader reader = new Reader("C:\\Users\\Benjamín\\git\\Mini-Interpreter\\testCode\\assign.rpql");
		Lexer lexer = new Lexer(reader.getBufferedReader());
		ArrayList<Token> tokens = new ArrayList<Token>();
		while (true) {
			//Busca la sgte expresion regular
			Token token = lexer.yylex();
			tokens.add(token);
			if (token.type == TokenType.EOF) {
				break;
			}
			System.out.println(token);
		}		
		Token[] tokensArray = new Token[tokens.size()];
		tokens.toArray(tokensArray);	
		Tokenizer tokenizer = new Tokenizer(tokensArray);
		Parser parser = new Parser(tokenizer);
		BlockNode program = parser.parseProgram();
		program.execute();
		
	}

}
