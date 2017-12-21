package ast;

import java.math.BigInteger;
import java.util.Scanner;

import interpreter.Interpreter;
import javafx.util.converter.BigIntegerStringConverter;

public class ReadNode implements ASTInstructionNode {

	String variableID; //La variable a la que va a asignar el valor leído.
	
	@Override
	public void execute() {
		
		Scanner scanner = new Scanner(System.in);
		String number = scanner.nextLine();
		if(!Interpreter.variableMap.containsKey(variableID)) Interpreter.variableMap.put(variableID, null);
		
		 Interpreter.variableMap.put(variableID, new BigInteger(number));
	}
	
	 

}
