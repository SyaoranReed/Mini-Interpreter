package interpreter;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

import ast.ASTInstructionNode;
import ast.BlockNode;
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
	
	
	public void sendExpectedTokenErrorMessage(TokenType expectedType, TokenType actualType) {
		
	}
	
	public Token nextToken() {
		return tokenizer.nextToken();
	}
}
