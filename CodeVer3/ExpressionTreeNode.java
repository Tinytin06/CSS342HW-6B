package homeworkSixB;

public class ExpressionTreeNode {
	private Token token;
	
	ExpressionTreeNode left;
	ExpressionTreeNode right;
	
	public ExpressionTreeNode(Token theToken, ExpressionTreeNode theLeft, ExpressionTreeNode theRight) {
		token = theToken;
		left = theLeft;
		right = theRight;
	}
	
	public ExpressionTreeNode(Token theToken) {
		token = theToken;
		left = null;
		right = null;
	}
	
	public ExpressionTreeNode() {
		
	}
}
