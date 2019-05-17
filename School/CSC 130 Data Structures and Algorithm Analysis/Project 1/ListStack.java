/*Warren Quattrocchi
 * CSC 130
 * Summer 2018
 */

import java.util.EmptyStackException;

public class ListStack implements DStack {
	//create a new node class
	private Node front;
	private double numberOfItems;
	//set number of items to 0 when a new stack is constructed
	public ListStack()
	{
		front = null;
		numberOfItems = 0;
	}
	
	//push method
	public void push(double d)
	{
		Node current = front;
		front = new Node(d);
		front.next = current;
		numberOfItems++;
	}
	
	//pop method
	public double pop()
	{
		if (isEmpty())
			throw new EmptyStackException();
		double data = front.data;
		front = front.next;
		numberOfItems--;
		return data;
	}
	
	//peek method
	public double peek()
	{
		if (isEmpty()) 
			 throw new EmptyStackException();
	    return front.data;
	}
	
	//check if stack is empty
	public boolean isEmpty()
	{
		if(numberOfItems == 0)
			return true;
		return false;
	}
	
	   //inner node class
	   private static class Node
	   {
	      private double data;
	      private Node next;
	      
	      //node constructor
	      public Node(double data)
	      {
	    	 //initialize data and set next node to null
	         this.data = data;
	         this.next = null;
	      }
	   }	
	
}
