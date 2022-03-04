package homeworkSixB;

import java.util.ArrayList;

/**
 * Represents one of the block in the spreadsheet.
 * @author Paul Lee (plee83@uw.edu)
 * @version Winter 2022
 *
 */
public class Cell {
	private String formula;
	private int value;

//	ArrayList<Object> dependsOn;
//	ArrayList<Object> feedInto;
	// The expression tree below represents the formula
	private ExpressionTree expressionTree;
	
	public Cell() {
		expressionTree = new ExpressionTree();
	}
	
	public void Evaluate(Spreadsheet spreadsheet) {
		CellToken cellToken = new CellToken();
		expressionTree = new ExpressionTree();
	}
}
