

public class LiteralToken extends Token {
	private int value;
	
	public LiteralToken(int theValue) {
		this.value = theValue;
	}

	public int getValue() {
		return value;
	}
}
