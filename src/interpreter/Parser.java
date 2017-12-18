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
	

	public void parseINST() {
		String nextToken = nextToken();
		
		switch(nextToken) {
			
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
		
		if( !nextToken().equals("(") ) return false;
		parseOPLOG();
		if( !nextToken().equals(")") ) return false;
		parseINSTS();
		if( !nextToken().equals("wend") ) return false;
		
		
		//Falta hacer el ciclo
		return true;
		
	}
	
	public ParserBooleanReturn parseOPLOG() {
		
		
		ParserIntegerReturn VAL1 = parseVAL();
		if( !VAL1.isParsed() ) return new ParserBooleanReturn(false, null);
		//OPLOG -> VAL == VAL | VAL != VAL | VAL <= VAL | VAL >= VAL | VAL < VAL | VAL > VAL
		
		String operator = nextToken();
		if(!operator.equals("==") || !operator.equals("!=") || 
		   !operator.equals("<=") || !operator.equals(">=") ||
		   !operator.equals("<") || !operator.equals(">")     ) return new ParserBooleanReturn(false, null);
		
		ParserIntegerReturn VAL2 = parseVAL();
		
		if( !VAL2.isParsed() ) return new ParserBooleanReturn(false, null);
		
		//Si se parseó correctamente, entonces retornamos el resultado de la operación lógica.
		if(operator.equals("==") ) return new ParserBooleanReturn(true, VAL1.value() == VAL2.value() );
		if(operator.equals("!=") ) return new ParserBooleanReturn(true, VAL1.value() != VAL2.value() );
		if(operator.equals("<=") ) return new ParserBooleanReturn(true, VAL1.value() <= VAL2.value() );
		if(operator.equals(">=") ) return new ParserBooleanReturn(true, VAL1.value() >= VAL2.value() );
		if(operator.equals("<") ) return new ParserBooleanReturn(true, VAL1.value() < VAL2.value() );
		if(operator.equals(">") ) return new ParserBooleanReturn(true, VAL1.value() > VAL2.value() );
		
		
		
	}
	
	
	public ParserIntegerReturn parseVAL() {
		
	}
	
	
	
	public String nextToken() {
		return this.tokenizer.nextToken();
	}
	
	public String previousToken() {
		return this.tokenizer.previousToken();
	}
	
	
	
	
	
}
