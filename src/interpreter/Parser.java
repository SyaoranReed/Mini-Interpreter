package interpreter;

import java.util.ArrayList;

import ast.ASTInstructionNode;
import ast.ArithmeticOperationNode;
import ast.AssignmentNode;
import ast.BlockNode;
import ast.ConditionNode;
import ast.IfNode;
import ast.NumberNode;
import ast.ReadNode;
import ast.ValueNode;
import ast.VariableNode;
import ast.WhileNode;
import ast.WriteNode;

public class Parser {

	private Tokenizer tokenizer;
	
	public Parser(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
		
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
		
		expect(TokenType.SEMICOLON);
		return instruction;
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
		ValueNode value1 = parseVAL();
		expect(TokenType.LOGICAL_OPERATOR);
		Token operator = currentToken();
		ValueNode value2 = parseVAL();
		
		return new ConditionNode(value1, value2,operator);
		
	}
	
	public ASTInstructionNode parseWhile() {
		expect(TokenType.WHILE);
		expect(TokenType.L_PARENTH);
		ConditionNode condition = parseCondition();
		expect(TokenType.R_PARENTH);
		BlockNode doBlock = parseDoInstructions();
		expect(TokenType.WEND);
		
		return new WhileNode(condition, doBlock);
	}
	
	public ASTInstructionNode parseWrite() {
		expect(TokenType.WRITE);
		ValueNode valueNode = parseVAL();
		
		return new WriteNode(valueNode);
	}
	
	
	public ASTInstructionNode parseRead() {
		expect(TokenType.READ);
		expect(TokenType.VAR);
		
		return new ReadNode(currentToken().value);
	}
	
	public ASTInstructionNode parseAssignment() {
		expect(TokenType.VAR);
		String variableID = currentToken().value;
		expect(TokenType.ASSIGN);
		ValueNode valueNode = parseVAL();
		
		return new AssignmentNode(variableID, valueNode);
	}
	
	public ValueNode parseVAL() {
		return parseArithmeticOperation();
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
				if(!(isCurrentTokenA(TokenType.VAR) || isCurrentTokenA(TokenType.INT))) {
					sendUnexpectedTokenArithmeticErrorMessage(TokenType.VAR, TokenType.INT, currentToken);
				}
				shuntingYard.add(currentToken);
			}
			else{
				if(isCurrentTokenA(TokenType.ARITHMETIC_OPERATOR)) {
					shuntingYard.add(currentToken);
				}
				else if(isCurrentTokenA(TokenType.LOGICAL_OPERATOR)) {
					break;
				}
				else {
					sendUnexpectedTokenErrorMessage(TokenType.ARITHMETIC_OPERATOR, currentToken);
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
