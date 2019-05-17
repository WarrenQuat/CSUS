/*
   Warren Quattrocchi
   csc 20
   Stack interface
*/

public interface Stack<Object>
{
   public int size();
   public boolean isEmpty();
   public Object pop();
   public void push(Object o);
   public Object peek(); 
}