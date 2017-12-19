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
		
		//Da problemas cuando programa está vacío.
		if(nextToken.startsWith("$")) parseASSGN();
		else if(nextToken.equals("if")) parseIF();
		else if(nextToken.equals("while")) parseWHILE();
		else if(nextToken.equals("read")) parseREAD();
		else if(nextToken.equals("write")) parseWRITE();
		
		if(!nextToken().equals(";")) return false;
		
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
	
	
	
	public boolean parseVAR() {
		String varToken = previousToken();
		if(!varToken.charAt(0) != '$') return false;
		
		//Agregar excepción para el caso en que haya solamente el $
		//Verificando que el segundo caracter del identificador de la variable sea una letra.
		
		String varID = varToken.substring(1, varToken.length());
		
		if( !Character.isLowerCase(varID.charAt(0)) ) return false;
		
		
			//Verificando que el resto del identificador corresponda a una secuencia alfanumérica.
			for(int i = 1; i < varID.length(); i++) {
				if( !(Character.isLowerCase(varID.charAt(i)) | Character.isDigit(varID.charAt(i)) ) ) return false; 
			}
		
		
		//Si tiene un identificador válido, entonces se agrega al diccionario de variables.
		if(!this.variableMap.containsKey(varID))
			this.variableMap.put(varID, null);
		
		
		return true;
		
	}
	
	public boolean parseN() {
		String numberToken = previousToken();
		for(int i = 0; i < numberToken.length() ; i++) {
			if(!Character.isDigit(numberToken.charAt(i))) return false;
		}
		return true;
	}
	
	
	public ParserIntegerReturn parseVAL() {
		
		if(tokenizer.lookAheadAllowed(2)) return new ParserIntegerReturn(false,null);
		
		String lookahead1 = tokenizer.lookAhead(1);
		String lookahead2 = tokenizer.lookAhead(2);
		
		if(lookahead2.equals("+") || lookahead2.equals("-") || lookahead2.equals("*") ||
		   lookahead2.equals("/") || lookahead2.equals("%")) parseOPAR();
		else if(lookahead1.startsWith("$")) parseVAR();
		else if(Character.isDigit(lookahead1.charAt(0)) ) parseN();
		
	}
	
	
	
	public String nextToken() {
		return this.tokenizer.nextToken();
	}
	
	public String previousToken() {
		return this.tokenizer.previousToken();
	}
	
	
	
	
	
}
