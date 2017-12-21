package ast;

import java.math.BigInteger;

public class NumberNode implements ValueNode{

	String number;
	
	public NumberNode(String number) {
		this.number = number;
	}

	@Override
	public BigInteger execute() {
		return new BigInteger(number);
	}
	
	
}
