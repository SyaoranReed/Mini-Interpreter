package ast;

public class WhileNode implements ASTInstructionNode  {

	ConditionNode condition;
	BlockNode doBlock;
	
	public WhileNode(ConditionNode condition, BlockNode doBlock) {
		this.condition = condition;
		this.doBlock = doBlock;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
