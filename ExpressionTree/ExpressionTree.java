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
			printTree(root);
		}
	}
	
	private void printTree(ExpressionTreeNode node) {
		if (node != null) {
			printTree(node.left);
			//System.out.print(node.token);
			printTree(node.right);
		}
	}

	/**
	 * Uses expressionTreeCalc to get the total value 
	 * and returns the total value.
	 * 
	 * @param spreadsheet
	 * @return the total value of the expression tree.
	 */
	public int Evaluate(Spreadsheet spreadsheet) {
		root = GetExpressionTree(myStack);
		return expressionTreeCalc(root, spreadsheet);
	}
	
	/**
	 * Calculates the expression tree.
	 * 
	 * @param root current node.
	 * @param spreadsheet for the rows and columns to be used for celltokens.
	 * @return the total after calculation
	 */
	private int expressionTreeCalc(ExpressionTreeNode root, Spreadsheet spreadsheet) {
		OperatorToken opToken = new OperatorToken();
		CellToken cellToken = new CellToken();
		
		if (root == null) {
			return 0;
		}
		
		expressionTreeCalc(root.left, spreadsheet);
		expressionTreeCalc(root.right, spreadsheet);
		
		// If current root token is a celltoken, convert it to the value of celltoken.
		if (root.token instanceof CellToken) {
			cellToken = (CellToken) root.token;
			root.token.setValue(spreadsheet.getSpreadsheetArray()[cellToken.getRow()-1][cellToken.getColumn()].getValue());
			System.out.println("Root: " + root.token.getValue());
			
		} 
		
		// If current root token is a operator token, 
		// either +,-,*,/ the left token value and the right token value.
		// The total is placed at the root.
		if (root.token instanceof OperatorToken) {
			opToken = (OperatorToken) root.token;
			if (opToken.getOperatorToken() == OperatorToken.Plus) {
				root.token.setValue(root.left.token.getValue() + root.right.token.getValue());
			}
			if (opToken.getOperatorToken() == OperatorToken.Minus) {
				root.token.setValue(root.left.token.getValue() - root.right.token.getValue());
			}
			if (opToken.getOperatorToken() == OperatorToken.Mult) {
				root.token.setValue(root.left.token.getValue() * root.right.token.getValue());
			}
			if (opToken.getOperatorToken() == OperatorToken.Div) {
				if (root.right.token.getValue() == 0) {
					throw new IllegalArgumentException("Cannot divide by 0!");
				}
				root.token.setValue(root.left.token.getValue() / root.right.token.getValue());
				
			}
			if (opToken.getOperatorToken() == OperatorToken.LeftParen) {
				System.out.println("Left Parenthesis works!");
				
			}
			if (opToken.getOperatorToken() == OperatorToken.RightParen) {
				System.out.println("Right Parenthesis works!");
				
			}
		}
		return root.token.getValue();
	}
	
	/**
	 * Gets the expression tree and set the root.
	 * @param theStack the stack of postfix expression
	 */
	public void BuildExpressionTree(Stack theStack) {
		root = GetExpressionTree(theStack);
		if (!theStack.isEmpty()) {
			System.out.println("Error in BuildExpressionTree.");
		}
	}
	
	/**
	 * Builds the expression tree.
	 * 
	 * @param theStack the stack of postfix expression.
	 * @return the expression tree.
	 */
	private ExpressionTreeNode GetExpressionTree(Stack theStack) {
		ExpressionTreeNode returnTree;
		Token token;
		
		if (theStack.isEmpty()) {
			return null;
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
			return returnTree;
		}
		return null;
	}
}
