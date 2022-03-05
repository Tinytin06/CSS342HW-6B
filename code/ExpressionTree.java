package homeworkSixB;

public class ExpressionTree {
	private ExpressionTreeNode root;
	private Stack myStack;
	
	public ExpressionTree() {
		
	}
	
	public ExpressionTree(Stack theStack) {
		myStack = theStack;
	}
	
	public void makeEmpty() {
		myStack.makeEmpty();
	}
	
	public void printTree() {
		if (myStack.isEmpty()) {
			System.out.println("Stack is empty!!");
		} else {
			BuildExpressionTree(myStack);
		}
	}

	/**
	 * 
	 * 
	 * @param spreadsheet
	 * @return
	 */
	public int Evaluate(Spreadsheet spreadsheet) {
		
		return 0;
	}
	
	public void BuildExpressionTree(Stack theStack) {
		root = GetExpressionTree(theStack);
		if (!theStack.isEmpty()) {
			System.out.println("Error in BuildExpressionTree.");
		}
	}
	
	private ExpressionTreeNode GetExpressionTree(Stack theStack) {
		ExpressionTreeNode returnTree;
		Token token;
		
		if (theStack.isEmpty()) {
			returnTree = new ExpressionTreeNode();
			return returnTree;
		}
		
		token = (Token) theStack.topAndPop();	// Need to handle stack underflow
		
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
