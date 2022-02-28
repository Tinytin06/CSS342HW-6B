

import java.util.Stack;

public class ExpressionTree {
	private ExpressionTreeNode root;
	Stack myStack;
	
	public ExpressionTree() {
		myStack = new Stack();
	}
	
	public void makeEmpty() {
		myStack.removeAllElements();
	}
	
	public void printTree() {
		BuildExpressionTree(myStack);
	}
	
	/**
	 * Calculate the equations in the subtree.
	 * 
	 * @param spreadsheet
	 * @return
	 */
	public int Evaluate(Spreadsheet spreadsheet) {
		return 0;
	}
	
	private void BuildExpressionTree(Stack theStack) {
		root = GetExpressionTree(myStack);
		if (!theStack.isEmpty()) {
			System.out.println("Error in BuildExpressionTree.");
		}
	}
	
	private ExpressionTreeNode GetExpressionTree(Stack theStack) {
		ExpressionTreeNode returnTree;
		Token token;
		
		if (theStack.isEmpty()) {
			return null;
		}
		
		token = (Token) theStack.pop();	// Need to handle stack underflow (Should be theStack.topAndPop();)
		
		if ((token instanceof LiteralToken) ||
			(token instanceof CellToken)) {
			
			// Literals and Cells are leaves in the expression tree
			returnTree = new ExpressionTreeNode(token, null, null);
			return returnTree;
		} else if (token instanceof OperatorToken) {
			// Continue finding tokens that will form the
			// right subtree and left subtree.
			ExpressionTreeNode rightSubtree = GetExpressionTree(theStack);
			ExpressionTreeNode leftSubtree = GetExpressionTree(theStack);
			returnTree = 
					new ExpressionTreeNode(token, leftSubtree, rightSubtree);
			return returnTree;
		}
		return null;
	}
}
