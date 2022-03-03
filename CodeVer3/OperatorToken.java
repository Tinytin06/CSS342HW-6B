package homeworkSixB;

public class OperatorToken extends Token {
	public static final char Plus = '+';
	public static final char Minus = '-';
	public static final char Mult = '*';
	public static final char Div = '/';
	public static final char LeftParen = '(';
	public static final char RightParen = ')';
	
	private char operatorToken;
	
	public OperatorToken() {
		
	}
	
	public OperatorToken(char theOperatorToken) {
		this.operatorToken = theOperatorToken;
	}
	
	public char getOperatorToken() {
		return this.operatorToken;
	}
	
	/*
	 * Return the priority of this OperatorToken.
	 *
	 * priorities:
	 *   +, - : 0
	 *   *, / : 1
	 *   (    : 2
	 *
	 * @return  the priority of operatorToken
	 */
	public int priority() {
		switch (this.operatorToken) {
        case Plus:
            return 0;
        case Minus:
            return 0;
        case Mult:
            return 1;
        case Div:
            return 1;
        case LeftParen:
            return 2;
        case RightParen:
        	return 2;

        default:
            // This case should NEVER happen
            System.out.println("Error in priority.");
            System.exit(0);
            break;
		}
		return -1;
	}
	
	// TODO: DELETES
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getOperatorToken() + "\n");
		return sb.toString();
	}
}
