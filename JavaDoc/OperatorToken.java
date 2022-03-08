package homeworkSixB;

/**
 * Token that contains the operator and sets the priority
 * of each of the operators.
 *
 * @version Winter 2022
 */
public class OperatorToken extends Token {
	/**
	 * Addition
	 */
	public static final char Plus = '+';
	
	/**
	 * Subtraction
	 */
	public static final char Minus = '-';
	
	/**
	 * Multiplication
	 */
	public static final char Mult = '*';
	
	/**
	 * Division
	 */
	public static final char Div = '/';
	
	/**
	 * The left parenthesis
	 */
	public static final char LeftParen = '(';
	
	/**
	 * The right parenthesis
	 */
	public static final char RightParen = ')';
	
	/**
	 * Operator token that contains one of the operations.
	 */
	private char operatorToken;
	
	/**
	 * Default constructor
	 */
	public OperatorToken() {
		
	}
	
	/**
	 * Constructor that initializes the operatorToken.
	 * @param theOperatorToken operator to be set to the operatorToken.
	 */
	public OperatorToken(char theOperatorToken) {
		this.operatorToken = theOperatorToken;
	}
	
	/**
	 * Gets the operator token.
	 * @return the operator token.
	 */
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
}
