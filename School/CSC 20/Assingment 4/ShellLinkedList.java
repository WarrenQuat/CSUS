/* The ShellLinkedList class
   Anderson, Franceschi
*/

public abstract class ShellLinkedList
{
  protected TeamNode head;
  protected int numberOfItems;

  //set the head to null
  //set the numberOfItems to 0
  public ShellLinkedList( )
  {
    this.head = null;
    numberOfItems = 0;
  }

  public int getNumberOfItems( )
  {
    return numberOfItems;
  }

  public boolean isEmpty( )
  {
    return (numberOfItems == 0);
  }
 
  public String toString( )
  {
     //returns a string representing all the items in the list
    return numberOfItems + " Teams in the list";
   
  }
}
