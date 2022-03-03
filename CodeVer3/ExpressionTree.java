package homeworkSixB;

public class ExpressionTree {
	private ExpressionTreeNode root;
	Stack myStack;
	
	public ExpressionTree() {
		myStack = new Stack();
	}
	
	public void makeEmpty() {
		myStack.makeEmpty();
	}
	
	public void printTree(Stack theStack) {
		if (theStack.isEmpty()) {
			System.out.println("Stack is empty!!");
		} else {
			BuildExpressionTree(theStack);
		}
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
	
	public void BuildExpressionTree(Stack theStack) {
		root = GetExpressionTree(myStack);
		System.out.println(root);
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
		
		token = theStack.topAndPop();	// Need to handle stack underflow
		
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
			System.out.println(returnTree);
			return returnTree;
		} else {
			returnTree = new ExpressionTreeNode();
			return returnTree;
		}
	}
}
