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

	ArrayList<Cell> dependsOn = new ArrayList<>();
	ArrayList<Cell> feedInto = new ArrayList<>();
	int        dist;   // Cost
	// The expression tree below represents the formula
	private ExpressionTree expressionTree;

	public void setFormula(String theFormula) {
		this.formula = theFormula;
	}

	public String getFormula() {
		return this.formula;
	}
	
	public Cell() {
		expressionTree = new ExpressionTree();
	}
	
	public void Evaluate(Spreadsheet spreadsheet) {
		CellToken cellToken = new CellToken();
		expressionTree = new ExpressionTree();
	}
}
