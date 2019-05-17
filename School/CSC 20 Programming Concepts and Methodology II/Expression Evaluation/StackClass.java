/*
   Warren Quattrocchi
   csc 20
   Stack class
*/

import java.util.*;

//generic stack
public class StackClass<Object> implements Stack<Object>
{
   ArrayList<Object> objects;
   
   public StackClass()
   {
      objects = new ArrayList<Object>();
   }
   
   //return the size of the list
   public int size()
   {
      return objects.size() - 1;
   }
   
   //check if list is empty
   public boolean isEmpty()
   {
      return objects.isEmpty();
   }
   
   //pop top item from list
   public Object pop()
   {  
      Object o = objects.get(size());
      objects.remove(size());
      return o;
      
   }
   
   //push item to top of list
   public void push(Object o)
   {
      objects.add(o);
   }
   
   //peek at the top item without removing it
   public Object peek()
   {
      return objects.get(size());
   }     
}