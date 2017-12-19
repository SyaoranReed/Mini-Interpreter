package interpreter;

import java.util.HashMap;

import com.sun.xml.internal.fastinfoset.vocab.ParserVocabulary;

import java.math.BigInteger;

public class Parser {

	private Tokenizer tokenizer;
	private HashMap<String, BigInteger> variableMap;

	public Parser(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
		this.variableMap = new HashMap<String, BigInteger>();

	}

	public void parseINSTS() {
		if (!tokenizer.hasNextToken()) {
			return;
		}

		parseINST();
		parseINSTS();
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
		ParserIntegerReturn va = parseVAR(true);
		if(!va.isParsed()) return false;
		if(!nextToken().equals("=")) return false; 
		if(!parseVAL().isParsed() ) return false;
		
		this.variableMap.put(varID, va.value());
		return true;
		
	}

	public boolean parseWHILE() {

		if (!nextToken().equals("(")) return false;

		// Guarda los valores para simular el if
		String izq = tokenizer.lookAhead(1);//
		String op = tokenizer.lookAhead(2);//
		String der = tokenizer.lookAhead(3);//
		BigInteger izqq = (izq.startsWith("$"))? variableMap.get(izq.substring(1)):new BigInteger(izq);//
		BigInteger derr = (izq.startsWith("$"))? variableMap.get(der.substring(1)):new BigInteger(der);//
		parseOPLOG();
		if (!nextToken().equals(")")) return false;
		while(logicOperation(izqq, op, derr)){//
			int index = tokenizer.currentIndex();//
			parseINSTS();
		}//
		if (!nextToken().equals("wend")) return false;

		// Falta hacer el ciclo
		return true;

	}

	private boolean logicOperation(BigInteger izq, String op, BigInteger der) {
		boolean result;
		switch (op) {
			case "<":
				result = izq.compareTo(der) < 0;
				break;
			case "<=":
				result = izq.compareTo(der) <= 0;
				break;
			case ">":
				result = izq.compareTo(der) > 0;
				break;
			case ">=":
				result = izq.compareTo(der) >= 0;
				break;
			case "!=":
				result = izq.compareTo(der) != 0;
				break;
			case "==":
				result = izq.compareTo(der) == 0;
				break;
		}
		return result;
	}

	public ParserBooleanReturn parseOPLOG() {

		ParserIntegerReturn VAL1 = parseVAL();
		if (!VAL1.isParsed()) return new ParserBooleanReturn(false, null);
		// OPLOG -> VAL == VAL | VAL != VAL | VAL <= VAL | VAL >= VAL | VAL < VAL | VAL
		// > VAL

		String operator = nextToken();
		if (!operator.equals("==") || !operator.equals("!=") || !operator.equals("<=") || !operator.equals(">=")
				|| !operator.equals("<") || !operator.equals(">"))
			return new ParserBooleanReturn(false, null);

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

		   lookahead2.equals("/") || lookahead2.equals("%")) return parserOPAR();
		else if(lookahead1.startsWith("$")) return parseVAR(false);

		else if(Character.isDigit(lookahead1.charAt(0)) ) return parseN();
		
		return failedParseIntegerReturn();
	}

	public ParserIntegerReturn failedParseIntegerReturn() {
		return new ParserIntegerReturn(false, null);
	}

	public ParserBooleanReturn failedParseBooleanReturn() {
		return new ParserBooleanReturn(false, null);
	}

	public String nextToken() {
		return this.tokenizer.nextToken();
	}

	public String previousToken() {
		return this.tokenizer.previousToken();
	}

	public ParserIntegerReturn parserOPAR() {
		String value = nextToken();
		BigInteger result = new BigInteger("0");

		while (value.compareTo(";") != 0) {
			if (value.matches("+|-")) continue;

			if (value.startsWith("$")) {
				value = parseVAR().value().toString();
			}

			// Obtengo el siguiente token
			String nextToken = "";
			if (tokenizer.lookAheadAllowed(1)) {
				nextToken = tokenizer.lookAhead(1);
			}
			// Obtengo el token que sera usado para el valor actual
			String beforeToken = "";
			if (tokenizer.lookBehindAllowed(1)) {
				beforeToken = tokenizer.lookBehind(1);
			}

			// Si hay 2 operadores seguidos el lenguaje esta mal
			if (value.matches("*|/|%|+|-") && !(beforeToken.matches("*|/|%|+|-") || nextToken.matches("*|/|%|+|-"))) {
				return failedParseIntegerReturn();
			}
			// paso a la siguiente iteracion
			if (value.matches("+|-")) continue;

			// Si el siguiente operador es una multiplicacion entonces este valor es parte
			// de la regla MD
			if (nextToken.matches("*|/|%")) {
				ParserIntegerReturn rtrn = parserMD();
				value = rtrn.value().toString();
			}
			switch (beforeToken) {
				case "-":
					result.subtract(new BigInteger(value));
					break;

				default:
					result.add(new BigInteger(value));
					break;
			}
			/*
			 * La multiplicacion si llega al punto y coma queda mirando en este, entonces
			 * esta condicion evita que avance el toquen y sale del while
			 */
			if (tokenizer.currentToken().compareTo(";") == 0) break;
			value = nextToken();
		}

		return new ParserIntegerReturn(true, result);
	}

	private ParserIntegerReturn parserMD() {
		String value = tokenizer.currentToken();
		BigInteger result = new BigInteger("1");

		while (value.compareTo(";") != 0) {

			if (value.startsWith("$")) {
				value = parseVAR().value().toString();
			}

			// Obtengo el siguiente token
			String nextToken = "";
			if (tokenizer.lookAheadAllowed(1)) {
				nextToken = tokenizer.lookAhead(1);
			}
			// Obtengo el token que sera usado para el valor actual
			String beforeToken = "";
			if (tokenizer.lookBehindAllowed(1)) {
				beforeToken = tokenizer.lookBehind(1);
			}

			// Si hay 2 operadores seguidos el lenguaje esta mal
			if (value.matches("*|/|%|+|-") && (beforeToken.matches("*|/|%|+|-") || nextToken.matches("*|/|%|+|-"))) {
				return failedParseIntegerReturn();
			}
			// paso a la siguiente iteracion
			if (value.matches("*|/|%")) continue;

			switch (beforeToken) {
				case "/":
					result.divide(new BigInteger(value));
					break;

				case "%":
					result.mod(new BigInteger(value));
					break;

				default:
					result.multiply(new BigInteger(value));
					break;
			}

			// Si el siguiente toquen es una suma termina el ciclo y vuelve al parser
			// anterior.
			if (nextToken.matches("+|-")) break;

			value = nextToken();
		}

		return new ParserIntegerReturn(true, result);
	}

}
