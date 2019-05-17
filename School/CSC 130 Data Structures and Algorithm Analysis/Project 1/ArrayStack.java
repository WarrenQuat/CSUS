/*Warren Quattrocchi
 * CSC 130
 * Summer 2018
 */

import java.util.EmptyStackException;

public class ArrayStack implements DStack{
	private double[] array;
	private int front;
	
	//initialize empty array with an initial size of 10
	//set front to -1, represents an empty stack
	public ArrayStack()
	{
		array = new double[10];
		front = -1;
	}
	
	//push method
	//will also check if array is full, and create a new array twice as big
	public void push(double d)
	{
		if (front >= array.length-1)
		{
			double newArray[] = new double[array.length * 2];
			for(int i = 0; i< array.length; i++)
			{
				newArray[i] = array[i];
			}
			array= newArray;
		}
		//push data onto stack and increment front
		array[front + 1] = d;
		front ++;		
	}
	
	//check if stack is empty
	public boolean isEmpty()
	{
		if (front == -1)
			return true;
		return false;
	}
	
	//pop top value from stack
	public double pop()
	{
		//throw a stack underflow exception if stack is empty
		if(isEmpty())
			throw new EmptyStackException();
		//return value
		double data = array[front];
		front--;
		return data;
	}
	
	//return top value without popping it
	public double peek()
	{
		if (isEmpty()) 
			 throw new EmptyStackException();
		return array[front];
	}
}
