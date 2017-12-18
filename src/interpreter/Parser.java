package interpreter;

import java.util.HashMap;
import java.math.BigInteger;

public class Parser {

	
	private Tokenizer tokenizer;
	private HashMap<String, BigInteger> variableMap;


	
	public Parser(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
		this.variableMap = new HashMap<String, BigInteger>();

	}
	

	public void parseInstruction() {
		TokenType nextToken = nextToken();
		
		switch(token.getValue()) {
			
			case "while":
				parseWHILE(); break;
			case "$":
				parseASSGN(); break;
			case "if": 
				parseIF(); break;
			case "write": 
				parseWRITE(); break;
			case "read": 
				parseREAD(); break;
			
		}
		
		if(!tokenizer.getNextToken().getValue().equals(";")) return false;
		
		return true;
	}
	
	public boolean parseWHILE() {
		
		if( !nextTokenValue().equals("(") ) return false;
		parseOPLOG();
		if( !nextTokenValue().equals(")") ) return false;
		
	}
	
	public ParserBooleanReturn parseOPLOG() {
		
		
		ParserIntegerReturn VAL1 = parseVAL();
		if( !VAL1.isParsed() ) return new ParserBooleanReturn(false, null);
		//OPLOG -> VAL == VAL | VAL != VAL | VAL <= VAL | VAL >= VAL | VAL < VAL | VAL > VAL
		String operator = nextTokenValue();
		if(!operator.equals("==") || !operator.equals("!=") || 
		   !operator.equals("<=") || !operator.equals(">=") ||
		   !operator.equals("<") || !operator.equals(">")     ) return new ParserBooleanReturn(false, null);
		ParserIntegerReturn VAL2 = parseVAL();
		if( !VAL2.isParsed() ) return new ParserBooleanReturn(false, null);
		
		//Si se parseó correctamente, entonces retornamos el resultado de la operación lógica.
		
		if(operator.equals("==") ) return new ParserBooleanReturn(true, VAL1.value() == VAL2.value() );
		if(operator.equals("!=") ) return new ParserBooleanReturn(true, VAL1.value() == VAL2.value() );
		if(operator.equals("<=") ) return new ParserBooleanReturn(true, VAL1.value() == VAL2.value() );
		if(operator.equals(">=") ) return new ParserBooleanReturn(true, VAL1.value() == VAL2.value() );
		if(operator.equals("<") ) return new ParserBooleanReturn(true, VAL1.value() == VAL2.value() );
		if(operator.equals(">") ) return new ParserBooleanReturn(true, VAL1.value() == VAL2.value() );
		
		
		
	}
	
	public ParserIntegerReturn parseVAL() {
		
	}
	
	
	
	public TokenType nextToken() {
		return this.tokenizer.nextToken();
	}
	
	public TokenType previousToken() {
		return this.tokenizer.previousToken();
	}
	
	public String nextTokenValue() {
		return this.tokenizer.nextTokenValue();
	}
	
	
	
	
}
