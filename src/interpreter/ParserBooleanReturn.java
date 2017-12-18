package interpreter;

import java.math.BigInteger;

public class ParserBooleanReturn {
	
	private boolean parsed; //Indica si el parseo fue exitoso.
	private Boolean value; //Resultado del parseo.
	
	public ParserBooleanReturn(boolean parsed, Boolean value) {
		this.parsed = parsed;
		this.value = value;
	}
	
	
	public boolean isParsed() {
		return parsed;
	}

	public boolean result() {
		return value;
	}
	
}
