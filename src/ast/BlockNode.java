package ast;

import java.util.ArrayList;

public class BlockNode {

	ArrayList<ASTInstructionNode> instructions = new ArrayList<ASTInstructionNode>();

	
	public void execute() {
		for(ASTInstructionNode instruction : instructions) {
			instruction.execute();
		}
		
	} 
	
	public void addInstruction(ASTInstructionNode childInstruction) {
		instructions.add(childInstruction);
	}
	
	
}
