package homeworkSixB;

import java.lang.Cloneable;
import java.util.EmptyStackException;

/**
 * Implements the stack class using array data structure.
 * This is used to store the tokens and used for the
 * expression tree.
 *
 * @version Winter 2022
 */
public class Stack implements Cloneable {
	/** 
	 * Stack stored in array 
	 */
	private Object[] myStack;
	
	/** 
	 * Index of the next available spot in the array. 
	 */
	private int topOfStack; 
	
	/**
	 * Default constructor that sets the size of the array to 10
	 * and makes the next available spot in the array to 0.
	 */
	public Stack() {
		this.myStack = new Object[10];
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
			return myStack[topOfStack];
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
		if (topOfStack + 1 == myStack.length) {
			Object[] newStack = new Object[myStack.length * 2];
			
			for (int i = 0; i < myStack.length; i++) {
				newStack[i] = myStack[i];
			}
			myStack = newStack;
		}
		myStack[++topOfStack] = item;
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
			return myStack[topOfStack--];
		}
	}
	
	/**
	 * Creates a clone of the stack.
	 * Purpose is to check each tokens in the stack by using
	 * topAndPop method without affecting the stack that we need
	 * to make the expression tree.
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
