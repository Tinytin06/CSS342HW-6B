package homeworkSixB;

/**
 * Parent class of CellToken, OperatorToken, and LiteralToken
 * 
 * @version Winter 2022
 */
public class Token {
	/**
	 * The value of the token.
	 */
	private int value;
	
	/**
	 * Default constructor
	 */
	public Token() {
		
	}
	
	/**
	 * Gets the value.
	 * @return the value.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Sets the value
	 * @param theValue to be set to the value.
	 */
	public void setValue(int theValue) {
		this.value = theValue;
	}
}
