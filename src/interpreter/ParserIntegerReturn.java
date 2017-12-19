package interpreter;

import java.math.BigInteger;

public class ParserIntegerReturn {
	
	private boolean parsed; //Indica si el parseo fue exitoso.
	private BigInteger value; //Resultado del parseo.
	
	public ParserIntegerReturn(boolean parsed, BigInteger value) {
		this.parsed = parsed;
		this.value = value;
	}

	public boolean isParsed() {
		return parsed;
	}

	public BigInteger value() {
		return value;
	}
	
	
}