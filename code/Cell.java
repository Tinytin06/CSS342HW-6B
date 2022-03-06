package homeworkSixB;

import java.util.ArrayList;

/**
 * Represents one of the block in the spreadsheet.
 * @version Winter 2022
 *
 */
public class Cell {
	private String formula;
	private int value;
	CellToken thisCell=new CellToken();
	// Cost
	int dist;
	
	ArrayList<Cell> dependsOn = new ArrayList<>();
	ArrayList<Cell> feedInto = new ArrayList<>();

	// The expression tree below represents the formula
	private ExpressionTree expressionTree;
	
	/**
	 * No-arg constructor that initializes the expressiontree.
	 */
	public Cell() {
		expressionTree = new ExpressionTree();
		formula = " ";
		value = 0;
	}
	
	/**
	 * Gets the integer value.
	 * @return the integer value.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Sets the integer value.
	 * @param theValue to be used to set the value.
	 */
	public void setValue(int theValue) {
		this.value = theValue;
	}
	
	/**
	 * Gets the string formula.
	 * @return the string formula.
	 */
	public String getFormula() {
		return this.formula;
	}
	
	/**
	 * Sets the string formula.
	 * @param theFormula to be used to set the formula.
	 */
	public void setFormula(String theFormula) {
		this.formula = theFormula;
	}
	
	public void Evaluate(Spreadsheet spreadsheet) {
		expressionTree = new ExpressionTree();
		expressionTree.printTree();
	}

	public ExpressionTree getExpressionTree() {
		return expressionTree;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Value: " + getValue() + "\n");
		sb.append("Formula: " + getFormula());
		return sb.toString();
	}
}
