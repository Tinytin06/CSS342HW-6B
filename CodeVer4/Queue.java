//package homeworkSixB;
//
//import java.util.EmptyStackException;
//
//public class Queue {
//	
//	private Object[] myQueue;
//	private int currentSize = 0;
//	private int front;
//	private int back;
//	
//	public Queue() {
//		myQueue = new Object[10];
//		front = 0;
//		back = -1;
//	}
//	
//	public boolean isEmpty() {
//		return currentSize == 0;
//	}
//	
//	public boolean isFull() {
//		return currentSize == myQueue.length;
//	}
//	
//	public void makeEmpty() {
//		currentSize = 0;
//		front = 0;
//		back = -1;
//	}
//	
//	public Object dequeue() {
//		if (isEmpty()) {
//			throw new EmptyStackException();
//		}
//		currentSize--;
//		Object frontItem = myQueue[front];
//		myQueue[front] = null;
//		front = increment(front);
//		return frontItem;
//	}
//	
//	public void enqueue(Object theObject) {
//		if (isFull()) {
//			//double size
//		}
//		back = increment(back);
//		myQueue[back] = theObject;
//		currentSize++;
//	}
//	
//	private int increment(int theX) {
//		if (++theX == myQueue.length) {
//			theX = 0;
//		}
//		return theX;
//	}
//}
