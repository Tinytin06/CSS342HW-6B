package homeworkSixB;

public class Spreadsheet {
	private static final int BadCell = -1;
	private int myRow;
	private int myColumn;
	private Cell[][] spreadsheetArray;
	
	/**
	 * No-arg constructor with default spreadsheet size of 5.
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
	 * Add cell to the spreadsheet.
	 * 
	 * @param theCellToken
	 * @param theFormula
	 */
	public void addCell(CellToken theCellToken, String theFormula) {
		Cell cell = new Cell();
		cell.setFormula(theFormula);
		this.spreadsheetArray[theCellToken.getRow()-1][theCellToken.getColumn()] = cell;
	}
	
	/**
	 * Prints all the values in the spreadsheet.
	 * Does not include 0.
	 * 
	 */
	public void printValues() {
		for (int row = 1; row < spreadsheetArray.length; row++) {
			for (int col = 0; col < spreadsheetArray[row-1].length; col++) {
				if (spreadsheetArray[row-1][col] != null && spreadsheetArray[row-1][col].getValue() != 0) {
					System.out.println(spreadsheetArray[row-1][col].getValue());
				}
			}
		}
//		Object[][] data = new Object[this.myRow][this.myColumn];
//		for (int i = 0; i < getNumRows(); i++) {
//			for (int j = 0; j < getNumColumns(); j++) {
//				if(this.spreadsheetArray[i][j] == null){
//					data[i][j] = null;
//				} else {
//					data[i][j] = spreadsheetArray[i][j].getValue();
//					System.out.println(spreadsheetArray[i][j].getValue());
//				}
//			}
//		}
	}
	
	/**
	 * Prints out the formula of the cell (ex. 5+A2)
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
	 * Prints all the formula in the spreadsheet.
	 */
	public void printAllFormulas() {
		for (int row = 1; row < spreadsheetArray.length; row++) {
			for (int col = 0; col < spreadsheetArray[row-1].length; col++) {
				if (spreadsheetArray[row-1][col] != null && spreadsheetArray[row-1][col].getValue() != 0) {
					System.out.println(spreadsheetArray[row-1][col].getFormula());
				}
			}
		}
//		Object[][] data = new Object[this.myRow][this.myColumn];
//		for (int i = 0; i < getNumRows(); i++) {
//			for (int j = 0; j < getNumColumns(); j++) {
//				if(this.spreadsheetArray[i][j] == null){
//					data[i][j] = null;
//				} else {
//					data[i][j] = spreadsheetArray[i][j].getFormula();
//					System.out.println(spreadsheetArray[i][j].getFormula());
//				}
//			}
//		}
		
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
		Graph graph = new Graph(expTreeTokenStack);
		Stack tokenStack = (Stack) expTreeTokenStack.clone();
		Token token = new Token();
		Cell cell = new Cell();
		
		// Find a way to specifically target cellTokens for topological sort.
		// A4 --> A2		if A2 has the formula 5+A4
		while(!tokenStack.isEmpty()) {
			token = (Token) tokenStack.topAndPop();
			if (token instanceof CellToken) {
				graph.addEdge((CellToken) token, cellToken, this.getSpreadsheetArray());
				graph.topSort(this);
			}
		}
		ExpressionTree eTree = new ExpressionTree(expTreeTokenStack);
		cell.setValue(eTree.Evaluate(this));
		spreadsheetArray[cellToken.getRow()-1][cellToken.getColumn()] = cell;
		/*while(!expTreeTokenStack.isEmpty()) {
			cellToken = (CellToken) expTreeTokenStack.top();
			
		}*/
	}
}
