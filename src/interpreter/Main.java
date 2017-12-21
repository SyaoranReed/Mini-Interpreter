package interpreter;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

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
			
		}		
		Token[] tokensArray = new Token[tokens.size()];
		tokens.toArray(tokensArray);	
		Tokenizer tokenizer = new Tokenizer(tokensArray);
		Parser parser = new Parser(tokenizer);
		BlockNode program = parser.parseProgram();
		
		try {
			program.execute();
		} catch (Exception e) {
			System.out.println("The program has syntax errors.");
			System.exit(0);
		}
	}
	
	public static void main3(String[] args) throws IOException {
		System.out.println("NUM");
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		System.out.println(n);
		if (n < 2) {
			System.out.println(n);
		}
		else {
			BigInteger fib1 = new BigInteger("1");
			BigInteger fib2 = new BigInteger("0");
			int i = 2;
			BigInteger act = new BigInteger("0");
			while (i <= n) {
				act = fib1.add(fib2);
				fib2 = fib1;
				fib1 = act;
				i = i + 1;
			}
			System.out.println(act);
		}
	}

}
