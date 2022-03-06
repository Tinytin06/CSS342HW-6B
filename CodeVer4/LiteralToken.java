package homeworkSixB;

public class LiteralToken extends Token {
	
	private int value;
	
	/**
	 * 
	 * @param theValue
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
	
	// TODO: DELETE
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		sb.append(getValue() + "\n");
//		return sb.toString();
//	}
}
