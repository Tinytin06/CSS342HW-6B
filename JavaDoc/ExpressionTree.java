package homeworkSixB;

/**
 * Binary tree 
 * @version Winter 2022
 *
 */
public class ExpressionTree {
	/**
	 * Root of the binary expression tree.
	 */
	private ExpressionTreeNode root;
	
	/**
	 * Stack of Tokens that is going to be used for the expression tree.
	 */
	private Stack myStack;
	
	/**
	 * Default constructor
	 */
	public ExpressionTree() {
		
	}
	
	/**
	 * Constructor that initializes the stack with the stack of Tokens.
	 * @param theStack
	 */
	public ExpressionTree(Stack theStack) {
		myStack = theStack;
	}
	
	/**
	 * Makes the stack (logically) empty.
	 */
	public void makeEmpty() {
		myStack.makeEmpty();
	}
	
	/**
	 * Calls the helper method printTree to print the contents
	 * of the tree.
	 */
	public void printTree() {
		if (myStack.isEmpty()) {
			System.out.println("Stack is empty!!");
		} else {
			root = GetExpressionTree(myStack);
			printTree(root);
		}
	}
	
	/**
	 * Helper method to print the tree.
	 * @param node to traverse the tree and print out the contents.
	 */
	private void printTree(ExpressionTreeNode node) {
		if (node != null) {
			printTree(node.left);
			System.out.print(node.token);
			printTree(node.right);
		}
	}

	/**
	 * Uses expressionTreeCalc to get the total value 
	 * and returns the total value.
	 * 
	 * @param spreadsheet to be used in the helper method.
	 * @return the total value of the expression tree.
	 */
	public int Evaluate(Spreadsheet spreadsheet) {
		root = GetExpressionTree(myStack);
		return expressionTreeCalc(root, spreadsheet);
	}
	
	/**
	 * Helper method that calculates the expression tree.
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
				if(root.right.token instanceof OperatorToken){
					if(((OperatorToken) root.right.token).getOperatorToken()==OperatorToken.Minus){
						root.token.setValue(root.left.token.getValue() + root.right.right.token.getValue());
						try {
							root.token.setValue(root.left.token.getValue() - root.right.token.getValue());
						} catch (NullPointerException e) {
							root.token.setValue(-1 * root.right.token.getValue());
						}
					}else {
						throw new IllegalArgumentException("Bad Cell");
					}
				} else{
					try{
						root.token.setValue(root.left.token.getValue() - root.right.token.getValue());
					} catch (NullPointerException e) {
						//System.out.println("test");
						root.token.setValue(-1 * root.right.token.getValue());
						
					}
				}
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
