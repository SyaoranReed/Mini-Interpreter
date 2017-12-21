package interpreter;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.regex.Pattern;

import ast.ASTInstructionNode;
import ast.BlockNode;
import ast.ConditionNode;
import ast.IfNode;

import java.math.BigInteger;

public class Parser {

	private Tokenizer tokenizer;
	public Parser(Tokenizer tokenizer) {
		
		
	}
	
	//Retorna el arbol de sintaxis abstracto.
	public BlockNode parseProgram() {
		return parseWholeProgramInstructions();
	}
	
	
	public BlockNode parseWholeProgramInstructions() {
		BlockNode wholeProgramBlock = new BlockNode();
		while(!isNextTokenA(TokenType.EOF)) {
			wholeProgramBlock.addInstruction(parseInstruction());
		}
		return wholeProgramBlock;
	}
	
	
	public ASTInstructionNode parseIf() {
		expect(TokenType.IF);
		expect(TokenType.L_PARENTH);
		ConditionNode condition = parseThenInstructions();
		if(isNextTokenA(TokenType.ELSE)){
			nextToken();
			parseElseInstructions();
		}
		
		expect(TokenType.ENDIF);
		
	}
	
	
	//Parsea las instrucciones que están dentro de un bloque asociado a un 'if'.
	public BlockNode parseThenInstructions() {
		BlockNode thenBlock = new BlockNode();
		while(!(isNextTokenA(TokenType.ENDIF) || isNextTokenA(TokenType.ELSE))) {
			thenBlock.addInstruction(parseInstruction());
		}
		return thenBlock;
	}
	
	//Parsea las instrucciones que están dentro de un bloque asociado a un 'if'.
	public BlockNode parseElseInstructions() {
		BlockNode elseBlock = new BlockNode();
		while(!(isNextTokenA(TokenType.ENDIF))) {
			elseBlock.addInstruction(parseInstruction());
		}
		
		return elseBlock;
	}
	
	
	
	//Devuelve el nodo de la instrucción parseada.
	public ASTInstructionNode parseInstruction() {
		
	}
	
	
	
	public boolean isNextTokenA(TokenType type) {
		return tokenizer.lookAhead(1).type == type;
	}
	
	public void expect(TokenType tokenType) {
		
		Token token = this.tokenizer.lookAhead(1);
		if(token.type == tokenType) {
			this.tokenizer.nextToken();
		}
		else {
			sendUnexpectedTokenErrorMessage(tokenType, token);
		}
	}
	
	
	public void sendUnexpectedTokenErrorMessage(TokenType expectedType, Token actualToken) {
		System.out.println("Error en la línea " +  actualToken.line + " columna " + actualToken.column);
		//System.out.println("Se esperaba un " + );
		//System.exit(arg0);
	}
	
	public Token nextToken() {
		return tokenizer.nextToken();
	}
}
