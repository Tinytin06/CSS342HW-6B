package homeworkSixB;

public class ExpressionTreeNode {
	Token token;
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
	
	// TODO: DELETE
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		sb.append(token);
//		return sb.toString();
//	}
}
