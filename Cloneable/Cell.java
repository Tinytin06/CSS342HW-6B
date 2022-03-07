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
	private CellToken thisCell=new CellToken();
	protected int dist;

	/**
	 * No-arg constructor that initializes the expressiontree.
	 */
	public Cell() {
		expressionTree = new ExpressionTree();
		formula = " ";
		value = 0;
	}
	ArrayList<Cell> dependsOn = new ArrayList<>();
	ArrayList<Cell> feedInto = new ArrayList<>();

	// The expression tree below represents the formula
	private ExpressionTree expressionTree;

	/**
	 * Sets the integer value.
	 * @param theValue to be used to set the value.
	 */
	public void setValue(int theValue) {
		this.value = theValue;
	}

	public void setFormula(String theFormula) {
		this.formula = theFormula;
	}

	public int getValue(){return this.value;}

	public String getFormula() {
		return this.formula;
	}

	public CellToken getThisCell() {
		return thisCell;
	}

	public void setThisCell(CellToken thisCell) {
		this.thisCell = thisCell;
	}

	public void Evaluate(Spreadsheet spreadsheet) {
		expressionTree = new ExpressionTree();
		expressionTree.printTree();
	}

	public ExpressionTree getExpressionTree() {
		return expressionTree;
	}
}
