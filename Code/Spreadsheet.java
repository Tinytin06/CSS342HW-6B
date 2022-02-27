package homeworkSixB;

import java.util.Stack;

public class Spreadsheet {
	private static final int ROW = 4;
	private static final int COLUMN = 4;
	private int[][] spreadsheetArray;
	
	public Spreadsheet(int theSize) {
		spreadsheetArray = new int[ROW][COLUMN];
	}
	
	public int getNumRows() {
		return ROW;
	}
	
	public int getNumColumns() {
		return COLUMN;
	}

	public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack expTreeTokenStack) {
		// TODO Auto-generated method stub
		
	}

	public void getCellToken(String inputCell, int startIndex, CellToken cellToken) {
		// TODO Auto-generated method stub
		
	}

	public void printAllFormulas() {
		// TODO Auto-generated method stub
		
	}

	public void printCellFormula(CellToken cellToken) {
		// TODO Auto-generated method stub
		
	}

	public void printValues() {
		// TODO Auto-generated method stub
		
	}
}
