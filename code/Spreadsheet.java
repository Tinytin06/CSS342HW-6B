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
		this.ROW= 5;
		this.COLUMN = 5;
		spreadsheetArray = new Cell[5][5];
	}

	/**
	 * Overloaded Constructor - sets row and column to parameter
	 * @param theSize - the size of the spreadsheet
	 */
	public Spreadsheet(int theSize) {
		this.ROW= theSize;
		this.COLUMN = theSize;
		spreadsheetArray = new Cell[theSize][theSize];
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
	 * 
	 */
	public void printValues() {
		//send to the GUI
		System.out.println();
	}
	
	/**
	 * 
	 * @param theCellToken
	 */
	public void printCellFormula(CellToken theCellToken) {
		System.out.println(spreadsheetArray[theCellToken.getRow()-1][theCellToken.getColumn()].getFormula());
	}
	
	public void printAllFormulas() {
		// TODO Auto-generated method stub
		
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
	 * 
	 * @param cellToken
	 * @param expTreeTokenStack
	 * @throws CloneNotSupportedException 
	 */
	public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack expTreeTokenStack) throws CloneNotSupportedException {
		Graph graph = new Graph();
		Stack tokenStack = (Stack) expTreeTokenStack.clone();
		Token token = new Token();
		
		// Find a way to specifically target cellTokens for topological sort.
		// A4 --> A2		if A2 has the formula 5+A4
		while(!tokenStack.isEmpty()) {
			token = (Token) tokenStack.topAndPop();
			if (token instanceof CellToken) {
				graph.addEdge(token, cellToken);
				System.out.println("Token: " + token);
			}
		}
		/*while(!expTreeTokenStack.isEmpty()) {
			cellToken = (CellToken) expTreeTokenStack.top();
			
		}*/
		ExpressionTree eTree = new ExpressionTree(expTreeTokenStack);
//		System.out.print(tokenStack);
//		System.out.print("\n" + expTreeTokenStack);
		eTree.printTree();
	}
}
