package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.regex.Pattern;

import ast.ASTInstructionNode;
import ast.ArithmeticOperationNode;
import ast.BlockNode;
import ast.ConditionNode;
import ast.IfNode;
import ast.NumberNode;
import ast.ValueNode;
import ast.VariableNode;
import sun.nio.ch.sctp.Shutdown;

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
		ASTInstructionNode instruction; 
		while(!isNextTokenA(TokenType.EOF)) {
			instruction = parseInstruction();
			if(instruction != null) wholeProgramBlock.addInstruction(instruction);
			
		}
		return wholeProgramBlock;
	}
	

	//Devuelve el nodo de la instrucción parseada.
	public ASTInstructionNode parseInstruction() {
		ASTInstructionNode instruction = null; 
		if(isNextTokenA(TokenType.IF)) instruction = parseIf(); 
		else if(isNextTokenA(TokenType.VAR)) instruction = parseAssignment();
		else if(isNextTokenA(TokenType.WHILE)) instruction = parseWhile();
		else if(isNextTokenA(TokenType.READ)) instruction = parseRead();
		else if(isNextTokenA(TokenType.WRITE)) instruction = parseWrite();
		
		if(isNextTokenA(TokenType.SEMICOLON) ) {
			if(instruction != null) expect(TokenType.SEMICOLON);
		}
	}
	
	
	
	
	public ASTInstructionNode parseIf() {
		expect(TokenType.IF);
		expect(TokenType.L_PARENTH);
		ConditionNode condition = parseCondition();
		BlockNode thenBlock = parseThenInstructions();
		BlockNode elseBlock = null;
		
		if(isNextTokenA(TokenType.ELSE)){
			nextToken();
			parseElseInstructions();
		}
		
		expect(TokenType.ENDIF);
		
		return new IfNode(condition, thenBlock, elseBlock);
		
		
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
	
	//Parsea las instrucciones que están dentro de un bloque asociado a un 'if'.
	public BlockNode parseDoInstructions() {
		BlockNode doBlock = new BlockNode();
		while(!(isNextTokenA(TokenType.WEND))) {
			doBlock.addInstruction(parseInstruction());
		}

		return doBlock;
	}

	public ConditionNode parseCondition() {
		
	}
	
	
	public boolean isNextTokenA(TokenType type) {
		return tokenizer.lookAhead(1).type == type;
	}
	
	public boolean isCurrentTokenA(TokenType type) {
		return tokenizer.currentToken().type == type;
	}
	
	public Token currentToken() {
		return tokenizer.currentToken();
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
	
	public ValueNode parseArithmeticOperation(){
		int i = 1;
		ShuntingYard shuntingYard = new ShuntingYard();
		while(!isNextTokenA(TokenType.SEMICOLON)) {
			Token currentToken = nextToken();
			//position is odd
			if(i++ % 2 != 0) {
				if(!isCurrentTokenA(TokenType.VAR) || !isCurrentTokenA(TokenType.INT)) {
					sendUnexpectedTokenArithmeticErrorMessage(TokenType.VAR, TokenType.INT, currentToken);
				}
				shuntingYard.add(currentToken);
			}
			else{
				if(isCurrentTokenA(TokenType.ARISMETIC)) {
					shuntingYard.add(currentToken);
				}
				else if(isCurrentTokenA(TokenType.LOGIC)) {
					break;
				}
				else {
					sendUnexpectedTokenErrorMessage(TokenType.ARISMETIC, currentToken);
				}
			}
		}
		ArrayList<String> postfix = shuntingYard.getPostfix();
		if(postfix.size() == 1) {
			String val = postfix.get(0);
			if (val.startsWith("$")) return new VariableNode(val);
			else return new NumberNode(val);
			
		}
		return new ArithmeticOperationNode(postfix);
	}
	
	public void sendUnexpectedTokenErrorMessage(TokenType expectedType, Token actualToken) {
		System.out.println("Error en la línea " +  actualToken.line + " columna " + actualToken.column);
		//System.out.println("Se esperaba un " + );
		//System.exit(arg0);
	}
	
	public void sendUnexpectedTokenArithmeticErrorMessage(TokenType expectedType1,TokenType expectedType2, Token actualToken) {
		System.out.println("Error en la línea " +  actualToken.line + " columna " + actualToken.column);
		//System.out.println("Se esperaba un " + );
		//System.exit(arg0);
	}
	
	public Token nextToken() {
		return tokenizer.nextToken();
	}
}
