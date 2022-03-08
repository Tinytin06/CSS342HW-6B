package homeworkSixB;

import java.util.ArrayList;

/**
 * Represents one of the block in the spreadsheet.
 * @version Winter 2022
 *
 */
public class Cell {
	/**
	 * String formula of the cell.
	 */
	private String formula;
	
	/**
	 * Value of the cell.
	 */
	private int value;
	
	/**
	 * To get the celltoken of the cell.
	 */
	CellToken thisCell = new CellToken();
	
	/**
	 * Cost.
	 */
	protected int dist;
	
	/**
	 * ArrayList to use for the graph to depend on.
	 */
	ArrayList<Cell> dependsOn = new ArrayList<>();
	
	/**
	 * ArrayList to use for the graph to feed into.
	 */
	ArrayList<Cell> feedInto = new ArrayList<>();
	
	/**
	 * Default constructor that initializes formula and value.
	 */
	public Cell() {
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
}
