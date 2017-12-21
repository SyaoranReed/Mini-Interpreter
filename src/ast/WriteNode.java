package ast;

public class WriteNode implements ASTInstructionNode {

	ValueNode valueNode; //El valor que va a imprimir.
	
	
	public WriteNode(ValueNode valueNode) {
	
		this.valueNode = valueNode;
	}


	@Override
	public void execute() {
		System.out.println(valueNode.execute());
		
	}

}
