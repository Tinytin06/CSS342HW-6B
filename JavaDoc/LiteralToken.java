package homeworkSixB;

/**
 * Represents the literal tokens 
 * (the values of the tokens).
 *
 * @version Winter 2022
 */
public class LiteralToken extends Token {
	
	/**
	 * The integer value of the token
	 */
	private int value;
	
	/**
	 * Constructor that initializes the value.
	 * @param theValue to set value.
	 */
	public LiteralToken(int theValue) {
		this.value = theValue;
	}
	
	/**
	 * Gets the value.
	 * @return the value.
	 */
	public int getValue() {
		return this.value;
	}
}
