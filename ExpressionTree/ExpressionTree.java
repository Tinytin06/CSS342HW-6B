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
	 * 
	 * 
	 * @param spreadsheet
	 * @return
	 */
	public int Evaluate(Spreadsheet spreadsheet) {
		Cell cell = new Cell();
		root = GetExpressionTree(myStack);
		cell.setValue(expressionTreeCalc(root));
		spreadsheet.getSpreadsheetArray()[0][0] = cell;
		System.out.println(expressionTreeCalc(root));
		return expressionTreeCalc(root);
	}
	
	private int expressionTreeCalc(ExpressionTreeNode root) {
		OperatorToken opToken = new OperatorToken();
		if (root == null) {
			return 0;
		}
		
		expressionTreeCalc(root.left);
		expressionTreeCalc(root.right);
		
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
			System.out.println(returnTree);
			return returnTree;
		}
		return null;
	}
}
