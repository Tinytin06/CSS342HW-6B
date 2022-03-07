package homeworkSixB;

import java.lang.Cloneable;
import java.util.EmptyStackException;

public class Stack implements Cloneable {
	/** Stack stored in array */
	private Object[] theStack;
	/** Index of the next available spot in the array. */
	private int topOfStack; 
	
	/**
	 * 
	 */
	public Stack() {
		this.theStack = new Object[10];
		this.topOfStack = -1;
	}
	
	/**
	 * Checks if the Stack is empty.
	 * 
	 * @return true if Stack is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return topOfStack == -1;
	}
	
	/**
	 * Makes the stack (logically) empty.
	 * 
	 * @return an empty stack.
	 */
	public int makeEmpty() {
		return topOfStack = -1;
	}
	
	/**
	 * Shows the top of the stack.
	 * 
	 * @return the item at the top of the stack.
	 * @throws EmptyStackException if the stack is empty.
	 */
	public Object top() {
		if (isEmpty()) {
			throw new EmptyStackException();
		} else {
			return theStack[topOfStack];
		}
	}
	
	/**
	 * Removes the item at the top of the stack.
	 * @throws EmptyStackException if stack is empty.
	 */
	public void pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		} else {
			topOfStack--;
		}
	}
	
	/**
	 * Pushes a new object into the top of the stack.
	 * If full, double the size of the array.
	 * 
	 * @param x the new item to be added.
	 */
	public void push(Object item) {
		if (topOfStack + 1 == theStack.length) {
			Object[] newStack = new Object[theStack.length * 2];
			
			for (int i = 0; i < theStack.length; i++) {
				newStack[i] = theStack[i];
			}
			theStack = newStack;
		}
		theStack[++topOfStack] = item;
	}
	
	/**
	 * Shows the item below the top of the stack.
	 * 
	 * @return the new item after the top of the stack gets removed.
	 * @throws EmptyStackException if stack is empty.
	 */
	public Object topAndPop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		} else {
			return theStack[topOfStack--];
		}
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	// TODO: DELETE
	public String toString() {
		StringBuilder sb = new StringBuilder();
//		while(!isEmpty()) {
//			sb.append(topAndPop());
//		}
		for (int i = 0; i < topOfStack; i++) {
			sb.append(theStack[i]);
		}
		return sb.toString();
	}
}
