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
	

	public boolean parseINST() {
		if(!tokenizer.lookAheadAllowed(1)) return false; 
		String lookahead = tokenizer.lookAhead(1);
		
		//Da problemas cuando programa está vacío.
		if(lookahead.startsWith("$")) return parseASSGN();
		else if(lookahead.equals("if")) return parseIF();
		else if(lookahead.equals("while")) return parseWHILE();
		else if(lookahead.equals("read")) return parseREAD();
		else if(lookahead.equals("write")) return parseWRITE();
		
		
		return true;
	}
	
	public boolean parseASSGN() {
		
		String varToken = tokenizer.lookAhead(1);
		if(tokenizer.lookAhead(1).length() == 1) return false;
		String varID = varToken.substring(1);
		if(!parseVAR(true).isParsed()) return false;
		if(!nextToken().equals("=")) return false; 
		if(!parseVAL().isParsed() ) return false;
		
		this.variableMap.put(varID, );
		
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
		int comp = VAL1.value().compareTo(VAL2.value());
		if(operator.equals("==") ) return new ParserBooleanReturn(true, comp == 0);
		if(operator.equals("!=") ) return new ParserBooleanReturn(true,  comp != 0 );
		if(operator.equals("<=") ) return new ParserBooleanReturn(true, comp < 0 || comp == 0);
		if(operator.equals(">=") ) return new ParserBooleanReturn(true, comp > 0  || comp == 0);
		if(operator.equals("<") ) return new ParserBooleanReturn(true, comp < 0);
		else /*(operator.equals(">") ) */ 
			return new ParserBooleanReturn(true, comp > 0);
		
		
		
		
	}
	
	
	//Si se llama al método de parseo desde una asignación, entonces el parseo se lleva a cabo correctamente
	//aún si la variable no está inicializada. Si se llama de cualquier otro lugar, significa que se necesita
	//el valor de la variable para llevar a cabo una instrucción, por lo que si no está incializada el parseo
	//se considera fallido. La bandera "fromAssignment" indica si se llamó desde el parseo de una asignación.
	public ParserIntegerReturn parseVAR(boolean fromAssignment) {
		String varToken = nextToken();
		if(varToken.length() == 1 || !varToken.startsWith("$")) return failedParseIntegerReturn();
		
		
		
		
		String varID = varToken.substring(1);
		//Verificando que el segundo caracter del identificador de la variable sea una letra.
		if( !Character.isLowerCase( varID.charAt(0)) ) return failedParseIntegerReturn();
		
		
			//Verificando que el resto del identificador corresponda a una secuencia alfanumérica.
			for(int i = 1; i < varID.length(); i++) {
				if( !(Character.isLowerCase(varID.charAt(i)) || Character.isDigit(varID.charAt(i)) ) ) return failedParseIntegerReturn(); 
			}
		
		
		//Si tiene un identificador válido, entonces se agrega al diccionario de variables.
		if(!this.variableMap.containsKey(varID))
			this.variableMap.put(varID, null);
		else if(!fromAssignment && this.variableMap.get(varID) == null) return failedParseIntegerReturn();
		else if(!fromAssignment && this.variableMap.get(varID) != null) return new ParserIntegerReturn(true,this.variableMap.get(varID) );
		
		return new ParserIntegerReturn(true, variableMap.get(varID));
		
	}
	
	
	
	public ParserIntegerReturn parseN() {
		String numberToken = previousToken();
		for(int i = 0; i < numberToken.length() ; i++) {
			if(!Character.isDigit(numberToken.charAt(i))) return failedParseIntegerReturn();
		}
		return new ParserIntegerReturn(true, new BigInteger(numberToken));
	}
	
	
	
	public ParserIntegerReturn parseVAL() {
		
		if(!tokenizer.lookAheadAllowed(2)) return failedParseIntegerReturn();
		
		String lookahead1 = tokenizer.lookAhead(1);
		String lookahead2 = tokenizer.lookAhead(2);
		
		if(lookahead2.equals("+") || lookahead2.equals("-") || lookahead2.equals("*") ||
		   lookahead2.equals("/") || lookahead2.equals("%")) return parseOPAR();
		else if(lookahead1.startsWith("$")) return parseVAR(false);
		else if(Character.isDigit(lookahead1.charAt(0)) ) return parseN();
		
		return failedParseIntegerReturn();
	}
	
	
	
	public ParserIntegerReturn failedParseIntegerReturn() {
		return new ParserIntegerReturn(false,null);
	}
	
	public ParserBooleanReturn failedParseBooleanReturn() {
		return new ParserBooleanReturn(false,null);
	}
	
	public String nextToken() {
		return this.tokenizer.nextToken();
	}
	
	public String previousToken() {
		return this.tokenizer.previousToken();
	}
	
	
	
	
	
}
