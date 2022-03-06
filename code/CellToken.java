package homeworkSixB;

/**
 * One of the node in the expression tree.
 * @author Paul Lee (plee83@uw.edu)
 * @version Winter 2022
 *
 */
public class CellToken extends Token {
	// column A = 0, B = 1, etc.
	private int column;
	private int row;
	
	public CellToken() {
		
	}
	
	/**
	 * Gets the column.
	 * @return the column.
	 */
	public int getColumn() {
		return this.column;
	}
	
	/**
	 * Gets the row.
	 * @return the row.
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Sets the column to theColumn.
	 * @param theColumn to set this.column
	 */
	public void setColumn(int theColumn) {
		this.column = theColumn;
	}
	
	/**
	 * Sets the row to theRow.
	 * @param theRow to set this.row
	 */
	public void setRow(int theRow) {
		this.row = theRow;
	}
	
	//TODO: DELETE
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getRow());
		sb.append(getColumn() + "\n");
		return sb.toString();
	}
}
