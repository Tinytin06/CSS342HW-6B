package homeworkSixB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Driver program of a spreadsheet application.
 * Text-based user interface.
 *
 * @author Donald Chinn
 */

public class SpreadsheetApp {
    
    private static final int BadCell = -1;

	/**
     * Read a string from standard input.
     * All characters up to the first carriage return are read.
     * The return string does not include the carriage return.
     * @return  a line of input from standard input
     */
    public static String readString() {
        BufferedReader inputReader;
        String returnString = "";
        char ch;
        
        inputReader = new BufferedReader (new InputStreamReader(System.in));
        
        // read all characters up to a carriage return and append them
        // to the return String
        try {
             returnString = inputReader.readLine();
        }
        catch (IOException e) {
            System.out.println("Error in reading characters in readString.");
        }
        return returnString;
    }
    
    /**
     * Option 'p'
     * @param theSpreadsheet
     */
    private static void menuPrintValues(Spreadsheet theSpreadsheet) {
        theSpreadsheet.printValues();
    }
    
    /**
     * Option 'f'
     * @param theSpreadsheet
     */
    private static void menuPrintCellFormula(Spreadsheet theSpreadsheet) {
        CellToken cellToken = new CellToken();
        String inputString;
    
        System.out.println("Enter the cell: ");
        inputString = readString();
        getCellToken(inputString, 0, cellToken);
    
        System.out.println(printCellToken(cellToken));
        System.out.println(": ");
    
        if ((cellToken.getRow() < 0) ||
            (cellToken.getRow() >= theSpreadsheet.getNumRows()) ||
            (cellToken.getColumn() < 0) ||
            (cellToken.getColumn() >= theSpreadsheet.getNumColumns())) {
            
            System.out.println("Bad cell.");
            return;
        }
    
        theSpreadsheet.printCellFormula(cellToken);
        System.out.println();
    }
    
    /**
     * Option 'a'
     * @param theSpreadsheet
     */
    private static void menuPrintAllFormulas(Spreadsheet theSpreadsheet) {
        theSpreadsheet.printAllFormulas();
        System.out.println();
    }
    
    /**
     * Option 'c'
     * @param theSpreadsheet
     * @throws CloneNotSupportedException 
     */
    private static void menuChangeCellFormula(Spreadsheet theSpreadsheet) throws CloneNotSupportedException {
        String inputCell;
        String inputFormula;
        CellToken cellToken = new CellToken();
        Stack expTreeTokenStack;
        // ExpressionTreeToken expTreeToken;
    
        System.out.println("Enter the cell to change: ");
        inputCell = readString();
        theSpreadsheet.getCellToken (inputCell, 0, cellToken);
    
        // error check to make sure the row and column
        // are within spreadsheet array bounds.
        if ((cellToken.getRow() < 0) ||
            (cellToken.getRow() >= theSpreadsheet.getNumRows()) ||
            (cellToken.getColumn() < 0) ||
            (cellToken.getColumn() >= theSpreadsheet.getNumColumns()) ) {
            
            System.out.println("Bad cell.");
            return;
        }
    
        System.out.println("Enter the cell's new formula: ");
        inputFormula = readString();
        theSpreadsheet.addCell(cellToken, inputFormula); // Added new method to add in a cell.
        expTreeTokenStack = getFormula(inputFormula);
    
        // This code prints out the expression stack from
        // top to bottom (that is, reverse of postfix).
        /*
        while (!expTreeTokenStack.isEmpty())
        {
        	Token expTreeToken = new Token();
            expTreeToken = (Token) expTreeTokenStack.topAndPop();
            //printExpressionTreeToken(expTreeToken);
            System.out.println(printExpressionTreeToken(expTreeToken));  // TODO: DELETE
        }*/
        
        theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack);
        System.out.println();
    }
    
    /**
     *  Given a CellToken, print it out as it appears on the
     *  spreadsheet (e.g., "A3")
     *  @param cellToken  a CellToken
     *  @return  the cellToken's coordinates
     */
    private static String printCellToken (CellToken cellToken) {
        char ch;
        String returnString = "";
        int col;
        int largest = 26;  // minimum col number with number_of_digits digits
        int number_of_digits = 2;

        col = cellToken.getColumn();

        // compute the biggest power of 26 that is less than or equal to col
        // We don't check for overflow of largest here.
        while (largest <= col) {
            largest = largest * 26;
            number_of_digits++;
        }
        largest = largest / 26;
        number_of_digits--;

        // append the column label, one character at a time
        while (number_of_digits > 1) {
            ch = (char) ((char) ((col / largest) - 1) + 'A'); // Added casting char for
            returnString += ch;								  // ((col / largest) - 1)
            col = col % largest;
            largest = largest  / 26;
            number_of_digits--;
        }

        // handle last digit
        ch = (char) (col + 'A'); // Added casting char
        returnString += ch;

        // append the row as an integer
        returnString += cellToken.getRow();

        return returnString;
    }
    
    /**
	 * Return a string associated with a token
	 * @param expTreeToken  an ExpressionTreeToken
	 * @return a String associated with expTreeToken
	 */
	private static String printExpressionTreeToken (Token expTreeToken) {
	    String returnString = "";

	    if (expTreeToken instanceof OperatorToken) {
	        returnString = ((OperatorToken) expTreeToken).getOperatorToken() + " ";
	    } else if (expTreeToken instanceof CellToken) {
	        returnString = printCellToken((CellToken) expTreeToken) + " ";
	    } else if (expTreeToken instanceof LiteralToken) {
	        returnString = ((LiteralToken) expTreeToken).getValue() + " ";
	    } else {
	        // This case should NEVER happen
	        System.out.println("Error in printExpressionTreeToken.");
	        System.exit(0);
	    }
	    return returnString;
	}
    
    /**
     * getCellToken
     * 
     * Assuming that the next chars in a String (at the given startIndex)
     * is a cell reference, set cellToken's column and row to the
     * cell's column and row.
     * If the cell reference is invalid, the row and column of the return CellToken
     * are both set to BadCell (which should be a final int that equals -1).
     * Also, return the index of the position in the string after processing
     * the cell reference.
     * (Possible improvement: instead of returning a CellToken with row and
     * column equal to BadCell, throw an exception that indicates a parsing error.)
     * 
     * A cell reference is defined to be a sequence of CAPITAL letters,
     * followed by a sequence of digits (0-9).  The letters refer to
     * columns as follows: A = 0, B = 1, C = 2, ..., Z = 25, AA = 26,
     * AB = 27, ..., AZ = 51, BA = 52, ..., ZA = 676, ..., ZZ = 701,
     * AAA = 702.  The digits represent the row number.
     *
     * @param inputString  the input string
     * @param startIndex  the index of the first char to process
     * @param cellToken  a cellToken (essentially a return value)
     * @return  index corresponding to the position in the string just after the cell reference
     */
    private static int getCellToken (String inputString, int startIndex, CellToken cellToken) {
        char ch;
        int column = 0;
        int row = 0;
        int index = startIndex;

        // HANDLE A BAD START INDEX
        if ((startIndex < 0) || (startIndex >= inputString.length() )) {
            cellToken.setColumn(BadCell);
            cellToken.setRow(BadCell);
            return index;
        }

        // GET RID OF LEADING WHITESPACE CHARACTERS
        while (index < inputString.length() ) {
            ch = inputString.charAt(index);            
            if (!Character.isWhitespace(ch)) {
                break;
            }
            index++;
        }
        if (index == inputString.length()) {
            // REACHED THE END OF THE STRING BEFORE FINDING A CAPITAL LETTER
            cellToken.setColumn(BadCell);
            cellToken.setRow(BadCell);
            return index;
        }

        // ASSERT: index now points to the first non-whitespace character

        ch = inputString.charAt(index);            
        // process CAPITAL alphabetic characters to calculate the column
        if (!Character.isUpperCase(ch)) {
            cellToken.setColumn(BadCell);
            cellToken.setRow(BadCell);
            return index;
        } else {
            column = ch - 'A';
            index++;
        }

        while (index < inputString.length() ) {
            ch = inputString.charAt(index);            
            if (Character.isUpperCase(ch)) {
                column = ((column + 1) * 26) + (ch - 'A');
                index++;
            } else {
                break;
            }
        }
        if (index == inputString.length() ) {
            // reached the end of the string before fully parsing the cell reference
            cellToken.setColumn(BadCell);
            cellToken.setRow(BadCell);
            return index;
        }

        // ASSERT: We have processed leading whitespace and the
        // capital letters of the cell reference

        // read numeric characters to calculate the row
        if (Character.isDigit(ch)) {
            row = ch - '0';
            index++;
        } else {
            cellToken.setColumn(BadCell);
            cellToken.setRow(BadCell);
            return index;
        }

        while (index < inputString.length() ) {
            ch = inputString.charAt(index);            
            if (Character.isDigit(ch)) {
                row = (row * 10) + (ch - '0');
                index++;
            } else {
                break;
            }
        }

        // successfully parsed a cell reference
        cellToken.setColumn(column);
        cellToken.setRow(row);
        return index;
    }
    
    /**
     * getFormula
     * 
     * Given a string that represents a formula that is an infix
     * expression, return a stack of Tokens so that the expression,
     * when read from the bottom of the stack to the top of the stack,
     * is a postfix expression.
     * 
     * A formula is defined as a sequence of tokens that represents
     * a legal infix expression.
     * 
     * A token can consist of a numeric literal, a cell reference, or an
     * operator (+, -, *, /).
     * 
     * Multiplication (*) and division (/) have higher precedence than
     * addition (+) and subtraction (-).  Among operations within the same
     * level of precedence, grouping is from left to right.
     * 
     * This algorithm follows the algorithm described in Weiss, pages 105-108.
     */
    private static Stack getFormula(String formula) {
        Stack returnStack = new Stack();  // stack of Tokens (representing a postfix expression)
        boolean error = false;
        char ch = ' ';

        int literalValue = 0;

        CellToken cellToken;
        int column = 0;
        int row = 0;

        int index = 0;  // index into formula
        Stack operatorStack = new Stack();  // stack of operators
        
        while (index < formula.length() ) {
            // get rid of leading whitespace characters
            while (index < formula.length() ) {
                ch = formula.charAt(index);
                if (!Character.isWhitespace(ch)) {
                    break;
                }
                index++;
            }

            if (index == formula.length() ) {
                error = true;
                break;
            }

            // ASSERT: ch now contains the first character of the next token.
            if (isOperator(ch)) {
                // We found an operator token
                switch (ch) {
                    case OperatorToken.Plus:
                    case OperatorToken.Minus:
                    case OperatorToken.Mult:
                    case OperatorToken.Div:
                    case OperatorToken.LeftParen:
                        // push operatorTokens onto the output stack until
                        // we reach an operator on the operator stack that has
                        // lower priority than the current one.
                        OperatorToken stackOperator;
                        while (!operatorStack.isEmpty()) {
                            stackOperator = (OperatorToken) operatorStack.top();
                            if ( (stackOperator.priority() >= operatorPriority(ch)) &&
                                (stackOperator.getOperatorToken() != OperatorToken.LeftParen) ) {

                                // output the operator to the return stack    
                                operatorStack.pop();
                                returnStack.push(stackOperator);
                            } else {
                                break;
                            }
                        }
                        break;

                    default:
                        // This case should NEVER happen
                        System.out.println("Error in getFormula.");
                        System.exit(0);
                        break;
                }
                // push the operator on the operator stack
                operatorStack.push(new OperatorToken(ch));

                index++;

            } else if (ch == ')') {    // maybe define OperatorToken.RightParen ?
                OperatorToken stackOperator;
                stackOperator = (OperatorToken) operatorStack.topAndPop();
                // This code does not handle operatorStack underflow.
                while (stackOperator.getOperatorToken() != OperatorToken.LeftParen) {
                    // pop operators off the stack until a LeftParen appears and
                    // place the operators on the output stack
                    returnStack.push(stackOperator);
                    stackOperator = (OperatorToken) operatorStack.topAndPop();
                }

                index++;
            } else if (Character.isDigit(ch)) {
                // We found a literal token
                literalValue = ch - '0';
                index++;
                while (index < formula.length()) {
                    ch = formula.charAt(index);
                    if (Character.isDigit(ch)) {
                        literalValue = (literalValue * 10) + (ch - '0');
                        index++;
                    } else {
                        break;
                    }
                }
                // place the literal on the output stack
                returnStack.push(new LiteralToken(literalValue));

            } else if (Character.isUpperCase(ch)) {
                // We found a cell reference token
                cellToken = new CellToken();
                index = getCellToken(formula, index, cellToken);
                if (cellToken.getRow() == BadCell) {
                    error = true;
                    break;
                } else {
                    // place the cell reference on the output stack
                    returnStack.push(cellToken);
                }

            } else {
                error = true;
                break;
            }
        }

        // pop all remaining operators off the operator stack
        while (!operatorStack.isEmpty()) {
            returnStack.push(operatorStack.topAndPop());
        }

        if (error) {
            // a parse error; return the empty stack
            returnStack.makeEmpty();
        }

        return returnStack;
    }
    
    /**
     * Given an operator, return its priority.
     *
     * priorities:
     *   +, - : 0
     *   *, / : 1
     *   (    : 2
     *
     * @param ch  a char
     * @return  the priority of the operator
     */
    private static int operatorPriority (char ch) {
        if (!isOperator(ch)) {
            // This case should NEVER happen
            System.out.println("Error in operatorPriority.");
            System.exit(0);
        }
        switch (ch) {
            case OperatorToken.Plus:
                return 0;
            case OperatorToken.Minus:
                return 0;
            case OperatorToken.Mult:
                return 1;
            case OperatorToken.Div:
                return 1;
            case OperatorToken.LeftParen:
                return 2;

            default:
                // This case should NEVER happen
                System.out.println("Error in operatorPriority.");
                System.exit(0);
                break;
        }
        return -1;
    }
    
    /**
     * Return true if the char ch is an operator of a formula.
     * Current operators are: +, -, *, /, (.
     * @param ch  a char
     * @return  whether ch is an operator
     */
    private static boolean isOperator (char ch) {
        return ((ch == OperatorToken.Plus) ||
                (ch == OperatorToken.Minus) ||
                (ch == OperatorToken.Mult) ||
                (ch == OperatorToken.Div) ||
                (ch == OperatorToken.LeftParen) );
    }
    
    public static void main(String[] args) throws CloneNotSupportedException {
        Spreadsheet theSpreadsheet = new Spreadsheet(30);

        boolean done = false;
        String command = "";
    
        System.out.println(">>> Welcome to the TCSS 342 Spreadsheet <<<");
        System.out.println();
        System.out.println();
    
        while (!done) {
            System.out.println("Choose from the following commands:");
            System.out.println();
            System.out.println("p: print out the values in the spreadsheet");
            System.out.println("f: print out a cell's formula");
            System.out.println("a: print all cell formulas");
            System.out.println("c: change the formula of a cell");
    /* BONUS
            System.out.println("r: read in a spreadsheet from a textfile");
            System.out.println("s: save the spreadsheet to a textfile");
     */
            System.out.println();
            System.out.println("q: quit");
    
            System.out.println();
            System.out.println("Enter your command: ");
            command = readString();
    
            // We care only about the first character of the string
            switch (command.charAt(0)) {
                case 'p':
                    menuPrintValues(theSpreadsheet);
                    break;
        
                case 'f':
                    menuPrintCellFormula(theSpreadsheet);
                    break;
        
                case 'a':
                    menuPrintAllFormulas(theSpreadsheet);
                    break;
        
                case 'c':
                    menuChangeCellFormula(theSpreadsheet);
                    break;
        
                    /* BONUS:
                case 'r':
                    menuReadSpreadsheet(theSpreadsheet);
                    break;
        
                case 's':
                    menuSaveSpreadsheet(theSpreadsheet);
                    break;
                    */
        
                case 'q':
                    done = true;
                    break;
        
                default:
                    System.out.println(command.charAt(0) + ": Bad command.");
                    break;
            }
            System.out.println();
    
        }
    
        System.out.println("Thank you for using our spreadsheet.");
    }

}