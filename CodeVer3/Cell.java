package homeworkSixB;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Represents one of the block in the spreadsheet.
 * @author Paul Lee (plee83@uw.edu)
 * @version Winter 2022
 *
 */
public class Cell {
	private String formula;
	private int value;

	ArrayList<Object> dependsOn;
	ArrayList<Object> feedInto;
	// The expression tree below represents the formula
	private ExpressionTree expressionTree;
	
	public void Evaluate(Spreadsheet spreadsheet) {
		
	}
}
