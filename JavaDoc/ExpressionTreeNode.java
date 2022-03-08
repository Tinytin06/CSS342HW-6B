package homeworkSixB;

/**
 * Represents each of the node in the expression tree.
 * Uses binary tree (not binary search tree).
 * 
 * @version Winter 2022
 */
public class ExpressionTreeNode {
	/**
	 * The current token.
	 */
	Token token;
	
	/**
	 * The left child.
	 */
	ExpressionTreeNode left;
	
	/**
	 * The right child.
	 */
	ExpressionTreeNode right;
	
	/**
	 * Constructor that sets the token, left child, and right child.
	 * @param theToken the token to be set.
	 * @param theLeft the left child to be set.
	 * @param theRight the right child to be set.
	 */
	public ExpressionTreeNode(Token theToken, ExpressionTreeNode theLeft, ExpressionTreeNode theRight) {
		token = theToken;
		left = theLeft;
		right = theRight;
	}
	
	/**
	 * Constructor that only sets the token.
	 * @param theToken the token to be set.
	 */
	public ExpressionTreeNode(Token theToken) {
		token = theToken;
		left = null;
		right = null;
	}
	
	/**
	 * Default constructor.
	 */
	public ExpressionTreeNode() {
		
	}

}
