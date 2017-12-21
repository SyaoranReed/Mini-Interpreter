package ast;

public class IfNode implements ASTInstructionNode {

	ConditionNode condition;
	BlockNode thenBlock;
	BlockNode elseBlock;
	
	public IfNode(ConditionNode conditionChild, BlockNode thenBlockChild, BlockNode elseBlockChild) {
		this.condition = conditionChild;
		this.thenBlock = thenBlockChild;
		this.elseBlock = elseBlockChild;
	}

	@Override
	public void execute() {
		if(condition.execute()) thenBlock.execute();
		else if(elseBlock!= null) elseBlock.execute(); 
		
	}
	
	
	
	
}
