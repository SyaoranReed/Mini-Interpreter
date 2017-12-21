package ast;

import java.math.BigInteger;

public class NumberNode implements ValueNode{

	BigInteger number;

	@Override
	public BigInteger execute() {
		return number;
	}
	
	
}
