package homeworkSixB;

public class Spreadsheet {
	private static final int BadCell = -1;
	private static int ROW;
	private static int COLUMN;
	private Cell[][] spreadsheetArray;
	/**
	 * Default Constructor - sets row and column to 5
	 */
	public Spreadsheet() {
		Cell cell = new Cell();
		this.ROW= 5;
		this.COLUMN = 5;
		spreadsheetArray =  new Cell[this.ROW][this.COLUMN];
		cell.setValue(0);
		for (int row = 0; row < this.ROW; row++) {
			for (int col = 0; col < this.COLUMN; col++) {
				spreadsheetArray[row][col] = cell;
			}
		}
	}

	/**
	 * Overloaded Constructor - sets row and column to parameter
	 * @param theSize - the size of the spreadsheet
	 */
	public Spreadsheet(int theSize) {
		Cell cell = new Cell();
		if (theSize <= 0) {
			throw new IllegalArgumentException("Spreadsheet cannot be less than or equal to 0");
		}
		this.ROW = theSize;
		this.COLUMN = theSize;
		spreadsheetArray = new Cell[this.ROW][this.COLUMN];
		cell.setValue(0);
		for (int row = 0; row < this.ROW; row++) {
			for (int col = 0; col < this.COLUMN; col++) {
				spreadsheetArray[row][col] = cell;
			}
		}
	}

	/**
	 * Add cell to the spreadsheet
	 *
	 * @param theCellToken
	 * @param theFormula
	 */
	public void addCell(CellToken theCellToken, String theFormula){
		Cell cell = new Cell();
		cell.setFormula(theFormula);
		cell.setThisCell(theCellToken);
		this.spreadsheetArray[theCellToken.getRow()-1][theCellToken.getColumn()] = cell;
	}


	/**
	 * @return ROW - the Row size
	 */
	public int getNumRows() {
		return ROW;
	}
	
	/**
	 * 
	 * @return COLUMN - the Column size
	 */
	public int getNumColumns() {
		return COLUMN;
	}

	/**
	 * Prints all formulas of spreadsheet into the GUI
	 */
	public void printValues() {
		//recalc all values
		recalcAll();

		Object[][] data = new Object[ROW][COLUMN];
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumColumns(); j++) {
				if(this.spreadsheetArray[i][j].getValue() == 0){
					data[i][j] = null;
				} else {
					data[i][j] = spreadsheetArray[i][j].getValue();
				}
			}
		}
		new JTableRowHeaders(data);
	}

	/**
	 * Prints the cell formula to the console
	 *
	 * @param theCellToken
	 */
	public void printCellFormula(CellToken theCellToken) {
		try{
			if (spreadsheetArray[theCellToken.getRow() - 1][theCellToken.getColumn()].getFormula() == null) {
				System.out.println("NO FORMULA FOUND");
			} else {
				System.out.println(spreadsheetArray[theCellToken.getRow() - 1][theCellToken.getColumn()].getFormula());
			}
		}catch (NullPointerException e){
			System.out.println("NO FORMULA FOUND");
			System.out.println();
			System.out.println("NullPointerException caught");
		}
	}

	/**
	 * Prints all formulas of spreadsheet into the GUI
	 */
	public void printAllFormulas() {
		Object[][] data = new Object[ROW][COLUMN];
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumColumns(); j++) {
				if(this.spreadsheetArray[i][j] == null){
					data[i][j] = null;
				} else {
					data[i][j] = spreadsheetArray[i][j].getFormula();
					System.out.println(spreadsheetArray[i][j].getFormula());
				}
			}
		}
		new JTableRowHeaders(data);
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
	 * This method is used to calculate the topological sort
	 *
	 * @param cellToken
	 * @param expTreeTokenStack
	 * @throws CloneNotSupportedException 
	 */
	public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack expTreeTokenStack) throws CloneNotSupportedException {
		Graph graph = new Graph();
		Stack tokenStack = (Stack) expTreeTokenStack.clone();
		Token token = new Token();
		Cell cell = new Cell();

		// Find a way to specifically target cellTokens for topological sort.
		// A4 --> A2		if A2 has the formula 5+A4
		while(!tokenStack.isEmpty()) {
			token = (Token) tokenStack.topAndPop();
			if (token instanceof CellToken) {
				graph.addEdge(token, cellToken);
				graph.addEdge((CellToken) token, cellToken, this.getSpreadsheetArray());

			}
			graph.topSort(this);
		}

	}

	public Cell[][] getSpreadsheetArray() {
		return this.spreadsheetArray;
	}

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
					try {
						this.changeCellFormulaAndRecalculate(cellToken,expTreeTokenStack);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
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
				returnStack.push(new LiteralToken(literalValue));

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
