package homeworkSixB;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A 2D spreadsheet array that consists of cells in each
 * array index. This class calls other classes
 * to update and change the spreadsheet. 
 * 
 * @version Winter 2022
 *
 */
public class Spreadsheet {
	/**
	 * When the user inputs a incorrect column and/or row.
	 */
	private static final int BadCell = -1;
	
	/**
	 * The number of rows in the spreadsheet
	 */
	private int myRow;
	
	/**
	 * The number of columns in the spreadsheet.
	 */
	private int myColumn;
	
	/**
	 * Keeps track of the contents of the spreadsheet.
	 */
	private Cell[][] spreadsheetArray;
	
	/**
	 * Default constructor with default spreadsheet size of 5.
	 */
	public Spreadsheet() {
		Cell cell = new Cell();
		this.myRow = 5;
		this.myColumn = 5;
		spreadsheetArray = new Cell[this.myRow][this.myColumn];
		cell.setValue(0);
		for (int row = 0; row < this.myRow; row++) {
			for (int col = 0; col < this.myColumn; col++) {
				spreadsheetArray[row][col] = cell;
			}
		}
	}
	
	/**
	 * Parameterized constructor that allows for the spreadsheet
	 * to be a different size.
	 * @param theSize the size of the spreadsheet
	 */
	public Spreadsheet(int theSize) {
		Cell cell = new Cell();
		if (theSize <= 0) {
			throw new IllegalArgumentException("Spreadsheet cannot be less than or equal to 0");
		}
		this.myRow = theSize;
		this.myColumn = theSize;
		spreadsheetArray = new Cell[this.myRow][this.myColumn];
		cell.setValue(0);
		for (int row = 0; row < this.myRow; row++) {
			for (int col = 0; col < this.myColumn; col++) {
				spreadsheetArray[row][col] = cell;
			}
		}
	}
	
	/**
	 * Gets the number of rows of the spreadsheet.
	 * @return the number of rows of the spreadsheet. 
	 */
	public int getNumRows() {
		return this.myRow;
	}
	
	/**
	 * Gets the number of columns of the spreadsheet.
	 * @return the number of columns of the spreadsheet.
	 */
	public int getNumColumns() {
		return this.myColumn;
	}

	/**
	 * Gets the 2d spreadsheet array.
	 * @return the 2d spreadsheet array.
	 */
	public Cell[][] getSpreadsheetArray() {
		return this.spreadsheetArray;
	}
	
	/**
	 * Adds cell to the spreadsheet.
	 * 
	 * @param theCellToken to know what cell to place the formula.
	 * @param theFormula the string input from the user.
	 */
	public void addCell(CellToken theCellToken, String theFormula) {
		Cell cell = new Cell();
		cell.setFormula(theFormula);
		cell.thisCell = theCellToken;
		this.spreadsheetArray[theCellToken.getRow()-1][theCellToken.getColumn()] = cell;
	}
	
	/**
	 * Displays all the values in the spreadsheet to the GUI.
	 * Does not include 0.
	 * 
	 */
	public void printValues() {
		Object[][] data = new Object[this.myRow][this.myColumn];
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumColumns(); j++) {
				if (this.spreadsheetArray[i][j].getValue() == 0) {
					data[i][j] = null;
				} else {
					data[i][j] = spreadsheetArray[i][j].getValue();
				}
			}
		}
		new JTableRowHeaders(data, this.myColumn);
	}
	
	/**
	 * Prints the formula of the cell (ex. 5+A2) to console.
	 * @param cellToken the cell token to be printed.
	 */
	public void printCellFormula(CellToken theCellToken) {
		try {
			if (spreadsheetArray[theCellToken.getRow() - 1][theCellToken.getColumn()].getFormula() == null) {
				System.out.println("NO FORMULA FOUND");
			} else {
				System.out.println(spreadsheetArray[theCellToken.getRow() - 1][theCellToken.getColumn()].getFormula());
			}
		} catch (NullPointerException e) {
			System.out.println("NO FORMULA FOUND");
			System.out.println();
			System.out.println("NullPointerException caught");
		}
	}
	
	/**
	 * Displays all the formula in the spreadsheet to the GUI.
	 * Does not include formulas that equals to 0.
	 */
	public void printAllFormulas() {
		Object[][] data = new Object[this.myRow][this.myColumn];
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumColumns(); j++) {
				if(this.spreadsheetArray[i][j] == null) {
					data[i][j] = null;
				} else {
					data[i][j] = spreadsheetArray[i][j].getFormula();
				}
			}
		}
		new JTableRowHeaders(data, this.myColumn);
		
	}

	/**
	 * getCellToken
	 * 
	 * Assuming that the next chars in a String (at the given startIndex)
	 * is a cell reference, set cellToken's column and row to the
	 * cell's column and row.
	 * If the cell reference is invalid, the row and column of the return CellToken
	 * are both set to BadCell (which should be a final int that equals -1).
	 * Also, return the index of the position in the string after processing
	 * the cell reference.
	 * (Possible improvement: instead of returning a CellToken with row and
	 * column equal to BadCell, throw an exception that indicates a parsing error.)
	 * 
	 * A cell reference is defined to be a sequence of CAPITAL letters,
	 * followed by a sequence of digits (0-9).  The letters refer to
	 * columns as follows: A = 0, B = 1, C = 2, ..., Z = 25, AA = 26,
	 * AB = 27, ..., AZ = 51, BA = 52, ..., ZA = 676, ..., ZZ = 701,
	 * AAA = 702.  The digits represent the row number.
	 *
	 * @param inputString the input string
	 * @param startIndex the index of the first char to process
	 * @param cellToken
	 * @return index corresponding to the position in the string just after the cell reference
	 */
	public int getCellToken(String inputString, int startIndex, CellToken cellToken) {
		char ch;
		int column = 0;
		int row = 0;
		int index = startIndex;
		
		// HANDLE A BAD STARTINDEX
		if ((startIndex < 0) || startIndex >= inputString.length()) {
			cellToken.setColumn(BadCell);
			cellToken.setRow(BadCell);
			return index;
		}
		
		// GET RID OF LEADING WHITESPACE CHARACTERS
		while (index < inputString.length()) {
			ch = inputString.charAt(index);
			if (!Character.isWhitespace(ch)) {
				break;
			}
			index++;
		}
		
		if (index == inputString.length()) {
			// REACHED THE END OF THE STRING BEFORE FINDING A CAPITAL LETTER
			cellToken.setColumn(BadCell);
			cellToken.setRow(BadCell);
			return index;
		}
		
		// ASSERT: INDEX NOW POINTS TO THE FIRST NON-WHITESPACE CHARACTER
		
		ch = inputString.charAt(index);
		// PROCESS CAPITAL ALPHABETIC CHARACTERS TO CALCULATE THE COLUMN
		if (!Character.isUpperCase(ch)) {
			cellToken.setColumn(BadCell);
			cellToken.setRow(BadCell);
			return index;
		} else {
			column = ch - 'A';
			index++;
		}
		
		while (index < inputString.length()) {
			ch = inputString.charAt(index);
			if (Character.isUpperCase(ch)) {
				column = ((column + 1) * 26) + (ch - 'A');
				index++;
			} else {
				break;
			}
		}
		
		if (index == inputString.length()) {
			// REACHED THE END OF THE STRING BEFORE FULLY PARSING THE CELL REFERENCE
			cellToken.setColumn(BadCell);
			cellToken.setRow(BadCell);
			return index;
		}
		
		// ASSERT: WE HAVE PROCESSED LEADING WHITESPACE AND THE
		// CAPITAL LETTERS OF THE CELL REFERENCE
		
		// READ NUMERIC CHARACTERS TO CALCULATE THE ROW
		if (Character.isDigit(ch)) {
			row = ch - '0';
			index++;
		} else {
			cellToken.setColumn(BadCell);
			cellToken.setRow(BadCell);
			return index;
		}
		
		while (index < inputString.length()) {
			ch = inputString.charAt(index);
			if (Character.isDigit(ch)) {
				row = (row * 10) + (ch - '0');
				index++;
			} else {
				break;
			}
		}
		
		// SUNCESSFULLY PARSED A CELL REFERENCE
		cellToken.setColumn(column);
		cellToken.setRow(row);
		return index;
	}
	
	/**
	 * Changes the cell formula in the cell based on user inputs,
	 * then calculates the formula.
	 * 
	 * @param cellToken to be added into the cell.
	 * @param expTreeTokenStack the stacks of tokens to be calculated.
	 * @throws CloneNotSupportedException if cloning is not supported.
	 */
	public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack expTreeTokenStack) throws CloneNotSupportedException {
		Graph graph = new Graph();
		Stack tokenStack = (Stack) expTreeTokenStack.clone(); // Stack is cloned to check the contents of the stack.
		Token token = new Token();
		Cell cell = new Cell();
		
		// Find a way to specifically target cellTokens for topological sort.
		// A4 --> A2		if A2 has the formula 5+A4
		while(!tokenStack.isEmpty()) {
			token = (Token) tokenStack.topAndPop();
			if (token instanceof CellToken) {
				graph.addEdge((CellToken) token, cellToken, this.getSpreadsheetArray());
			}
		}
		graph.topSort(this);
		recalcAll();
	}
	
	/**
	 * Recalculates the formulas in the spreadsheet if a cell changes and another
	 * cell is using that cell as part of its formula.
	 */
	public void recalcAll(){
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumColumns(); j++) {
				if(this.spreadsheetArray[i][j].getFormula() != null) {
					CellToken cellToken = new CellToken();
					cellToken.setColumn(j);
					cellToken.setRow(i+1);
					String inputFormula = this.spreadsheetArray[i][j].getFormula();
					Stack expTreeTokenStack;
					expTreeTokenStack = getFormula(inputFormula);
					
					ExpressionTree eTree = new ExpressionTree(expTreeTokenStack);
					this.spreadsheetArray[cellToken.getRow()-1][cellToken.getColumn()].setValue(eTree.Evaluate(this));

				}
			}
		}

	}
	
	/**
	 * getFormula
	 *
	 * Given a string that represents a formula that is an infix
	 * expression, return a stack of Tokens so that the expression,
	 * when read from the bottom of the stack to the top of the stack,
	 * is a postfix expression.
	 *
	 * A formula is defined as a sequence of tokens that represents
	 * a legal infix expression.
	 *
	 * A token can consist of a numeric literal, a cell reference, or an
	 * operator (+, -, *, /).
	 *
	 * Multiplication (*) and division (/) have higher precedence than
	 * addition (+) and subtraction (-).  Among operations within the same
	 * level of precedence, grouping is from left to right.
	 *
	 * This algorithm follows the algorithm described in Weiss, pages 105-108.
	 */
	private Stack getFormula(String formula) {
		Stack returnStack = new Stack();  // stack of Tokens (representing a postfix expression)
		boolean error = false;
		char ch = ' ';

		int literalValue = 0;

		CellToken cellToken;
		int column = 0;
		int row = 0;
		
		boolean negate = false;

		int index = 0;  // index into formula
		Stack operatorStack = new Stack();  // stack of operators

		while (index < formula.length() ) {
			// get rid of leading whitespace characters
			while (index < formula.length() ) {
				ch = formula.charAt(index);
				if (!Character.isWhitespace(ch)) {
					break;
				}
				index++;
			}

			if (index == formula.length() ) {
				error = true;
				break;
			}

			// ASSERT: ch now contains the first character of the next token.
			if (isOperator(ch)) {
				// We found an operator token
				switch (ch) {
					case OperatorToken.Plus:
					case OperatorToken.Minus:
					case OperatorToken.Mult:
					case OperatorToken.Div:
					case OperatorToken.LeftParen:
						// push operatorTokens onto the output stack until
						// we reach an operator on the operator stack that has
						// lower priority than the current one.
						OperatorToken stackOperator;
						while (!operatorStack.isEmpty()) {
							stackOperator = (OperatorToken) operatorStack.top();
							if ( (stackOperator.priority() >= operatorPriority(ch)) &&
									(stackOperator.getOperatorToken() != OperatorToken.LeftParen) ) {

								// output the operator to the return stack
								operatorStack.pop();
								returnStack.push(stackOperator);
							} else {
								break;
							}
						}
						break;

					default:
						// This case should NEVER happen
						System.out.println("Error in getFormula.");
						System.exit(0);
						break;
				}
				// push the operator on the operator stack
				operatorStack.push(new OperatorToken(ch));
				if (isOperator(formula.charAt(index+1))) {
					if (formula.charAt(index+1) == OperatorToken.Minus) {
						negate = true;
						index++;
					}
				}

				index++;

			} else if (ch == ')') {    // maybe define OperatorToken.RightParen ?
				OperatorToken stackOperator;
				stackOperator = (OperatorToken) operatorStack.topAndPop();
				// This code does not handle operatorStack underflow.
				while (stackOperator.getOperatorToken() != OperatorToken.LeftParen) {
					// pop operators off the stack until a LeftParen appears and
					// place the operators on the output stack
					returnStack.push(stackOperator);
					stackOperator = (OperatorToken) operatorStack.topAndPop();
				}

				index++;
			} else if (Character.isDigit(ch)) {
				// We found a literal token
				literalValue = ch - '0';
				index++;
				while (index < formula.length()) {
					ch = formula.charAt(index);
					if (Character.isDigit(ch)) {
						literalValue = (literalValue * 10) + (ch - '0');
						index++;
					} else {
						break;
					}
				}
				// place the literal on the output stack
				if (negate == true) {
					returnStack.push(new LiteralToken(-literalValue));
					negate = false;
				} else {
					returnStack.push(new LiteralToken(literalValue));
				}

			} else if (Character.isUpperCase(ch)) {
				// We found a cell reference token
				cellToken = new CellToken();
				index = getCellToken(formula, index, cellToken);
				if (cellToken.getRow() == BadCell) {
					error = true;
					break;
				} else {
					// place the cell reference on the output stack
					returnStack.push(cellToken);
				}

			} else {
				error = true;
				break;
			}
		}

		// pop all remaining operators off the operator stack
		while (!operatorStack.isEmpty()) {
			returnStack.push(operatorStack.topAndPop());
		}

		if (error) {
			// a parse error; return the empty stack
			returnStack.makeEmpty();
		}

		return returnStack;
	}

	/**
	 * Given an operator, return its priority.
	 *
	 * priorities:
	 *   +, - : 0
	 *   *, / : 1
	 *   (    : 2
	 *
	 * @param ch  a char
	 * @return  the priority of the operator
	 */
	private static int operatorPriority (char ch) {
		if (!isOperator(ch)) {
			// This case should NEVER happen
			System.out.println("Error in operatorPriority.");
			System.exit(0);
		}
		switch (ch) {
			case OperatorToken.Plus:
				return 0;
			case OperatorToken.Minus:
				return 0;
			case OperatorToken.Mult:
				return 1;
			case OperatorToken.Div:
				return 1;
			case OperatorToken.LeftParen:
				return 2;

			default:
				// This case should NEVER happen
				System.out.println("Error in operatorPriority.");
				System.exit(0);
				break;
		}
		return -1;
	}

	/**
	 * Return true if the char ch is an operator of a formula.
	 * Current operators are: +, -, *, /, (.
	 * @param ch  a char
	 * @return  whether ch is an operator
	 */
	private static boolean isOperator (char ch) {
		return ((ch == OperatorToken.Plus) ||
				(ch == OperatorToken.Minus) ||
				(ch == OperatorToken.Mult) ||
				(ch == OperatorToken.Div) ||
				(ch == OperatorToken.LeftParen) );
	}

}
