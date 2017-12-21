package interpreter;

import java.math.BigInteger;
import java.util.HashMap;

import ast.BlockNode;

public class Interpreter {

	static public HashMap<String, BigInteger> variableMap = new HashMap<String, BigInteger>();
	BlockNode program;
	
	public Interpreter(BlockNode program) {
		this.program = program;
		
	}
	
	public void interprete() {
		program.execute();
	}
}
