/* The TeamLinkedList class
   Quattrocchi,Warren
   
*/

import java.util.*;

public class TeamLinkedList extends ShellLinkedList
{
  // head and numberOfItems are inherited instance variables
  public TeamLinkedList(  )
  {
   super();
  }

  /** insert method
  *   @param    t   Team object to insert
  */
  public void insert( Team t )
  {
    // create a TeamNode object and store it in tn
    TeamNode tn = new TeamNode(t);
    //call the method setNext on the object tn with the parameter head
    tn.setNext(head);
    //set the head to tn
    head = tn;
    //increment numberOfItems
    numberOfItems++;
  }

  /** delete method
  *   @param    searchNickname nickname of Team to delete
  *   @return   the Team deleted
  */
 public Team delete(String searchNickname)
                            throws DataStructureException
  {
    //make a copy of the  head node called current 
    //create an objcet of TeamNode called previous and set it to null
    TeamNode current = head;
    TeamNode previous = null;
    if(current == null)
      throw new DataStructureException("No teams in list");
    Team t = current.getTeam();

    if (t.getNickname().equals(searchNickname))
    {
          // delete head and adjust the new head
          head = head.getNext();
          numberOfItems--;
          return t;
    }else{
        t = current.getTeam();
        while(current !=null)
        {
         if(t.getNickname().equals(searchNickname))
         {
            previous.setNext(current.getNext());
            previous = current;
            numberOfItems--;
            return current.getTeam();
         
            
         }else{
            previous = current;
            current = current.getNext();
            if(current !=null)   
            t = current.getTeam();
         }
       }        
    
    }
      throw new DataStructureException(searchNickname +  " not found: cannot be deleted");
  }

  /** orderTeams method
  *   @return   an ArrayList of the teams ordered in descending order by winning percentages
  */
  public ArrayList<Team> orderTeams( )
  {
    ArrayList<Team> list = new ArrayList<Team>( );
    // Fill list with the linked list of teams
    TeamNode current = head;

    while ( current != null )
    {
      list.add( current.getTeam( ) );
      current = current.getNext( );
    }

    ArrayList<Team> orderedList = new ArrayList<Team>( );
    // fill orderedList from list using a modified version of selection sort
    // sorting in descending order by winning percentages
    double currentMaxWinPercentage = 0;
    Team temp, tempMaxWP;

    for (int i = 0; i < numberOfItems; i++ )
    {
      // find best team
      tempMaxWP = ( Team ) list.get( 0 );
      for ( int j = 1; j < list.size( ); j++ )
      {
        temp = ( Team ) list.get( j );
        if ( temp.winningPercentage( ) > tempMaxWP.winningPercentage( ) )
          tempMaxWP = temp;
      }

      // add tempMaxWP to orderedList
      orderedList.add( tempMaxWP );
      // remove it from list
      list.remove( tempMaxWP );
    }

    return orderedList;
  }

  /** fiveBestTeams method
  *   @return   an ArrayList representing the five best teams based on winning percentage
  */
  public ArrayList<Team> fiveBestTeams( )
  {
    ArrayList<Team> result = new ArrayList<Team>( );
    //create an orderd list of the teams 
    ArrayList<Team> ordered = orderTeams( );
    Team temp;

    if ( ordered.size( ) < 5 )
    {
      for ( int i = 0; i < ordered.size( ); i++ )
      {
        //get the item at i in the ordered list in the variable temp 
        temp = ordered.get(i);
        //get the name of the team stored in temp
        String name = temp.getNickname();
        //get the wins of the team stored in temp
        int wins = temp.getWins();
        //get the losses of the team stored in temp
        int losses = temp.getLosses();
        //cretae an object of Team
        Team t = new Team(name,wins,losses);
        //add the object to the array list result
        result.add(t);
              }
    }
    else
    {
      for ( int i = 0; i < 5; i++ )
      {
        //get the item at i in the ordered list in the variable temp 
        temp = ordered.get(i);
        //get the name of the team stored in temp
        String name = temp.getNickname();
        //get the wins of the team stored in temp
        int wins = temp.getWins();
        //get the losses of the team stored in temp
        int losses = temp.getLosses();
        //cretae an object of Team
        Team t = new Team(name,wins,losses);
        //add the object to the array list result
        result.add(t);
      }
    }
    return result;
  }

  /** maxWins method
  *   @return   an int, the maximum number of wins by any team
  */
  public int maxWins( )
  {
    int max = 0; // number of wins cannot be negative
    //copy the head node in the variable called current
    TeamNode current = head;
     
   // while current is not null
   while (current != null)
    {
      Team t = current.getTeam();
     //if the wins of the team stored in the current is gretaer than mxa
      if(t.getWins() > max)
         {
            max = t.getWins();
            // set max to the wins of the team      
         }   
           // set the current to the next node
         current = current.getNext();    
    }
    return max;
  }


  /** mostWins method
  *   @return   an ArrayList of Strings storing all the nicknames of the teams with the most wins
  */
  public ArrayList<String> mostWins( )
  {
    int maxWins = maxWins( );
    ArrayList<String> temp = new ArrayList<String>( );
    //make a copy of the head node and call it current
    TeamNode current = head;
   // while current is not null 
   while(current != null)
    {
      Team t = current.getTeam();
      // if the wins of the current team is equal to maxWins
      if(t.getWins() == maxWins)
      {
            //add the current team nikname to the temp
            temp.add(t.getNickname());
      }      
      //adjust current to the next node 
      current = current.getNext();
    }

    return temp;
  }

  /** peek method
  *   @param    searchNickname nickname of Team to search for
  *   @return   a copy of the Team found
  */
  public Team peek( String searchNickname )
      throws DataStructureException
  {
    //make a copy of the head node and store it in the current 
    TeamNode current = head;
    if(current == null)
      throw new DataStructureException("No teams in list");
    Team t = current.getTeam();
    
    //while  current is not null and the searchNickName is not equals to the nukName of the current node
    while(current != null)
    {
       if(t.getNickname().equals(searchNickname))
       {
         return t;
       }else{   
         current = current.getNext();
         if (current !=null)
         t = current.getTeam();
       }
      
  }
   throw new DataStructureException(searchNickname + " not found");
  }
  
  //return the first team in the list
  public Team returnFirst()throws DataStructureException
  {
   TeamNode current = head;
   if(current == null)
      throw new DataStructureException("No teams in list");
   return current.getTeam();
  }
  
  //reverse the list
  public ArrayList<Team> reverseList()
  {
   //create a new arraylist anfd initialize it by running through the linkedlist
   ArrayList<Team> reversed = new ArrayList<Team>();
   TeamNode current = head;
   Team t;
   while(current !=null)
   {
     t  = current.getTeam();
     String name = t.getNickname();
     int wins = t.getWins();
     int losses = t.getLosses();
     reversed.add(new Team(name,wins,losses));
     current = current.getNext();
   }
  //reverse the list by calling a method from Collections
  Collections.reverse(reversed);
  System.out.println(super.toString());
  //return the ArrayList
  return reversed;
  } 
  
  //ToString method, combines all toStrings of the team objects within the linkedlist
  public String toString()
   {  
      if(numberOfItems == 0)
         return "No teams in list";
      else{
      Team t;
      String s = super.toString() + "\n";
      TeamNode current = head;
     // System.out.println(super.toString());
      while(current != null)
      {
         t = current.getTeam();
         s = s + t.toString();
         current = current.getNext();
      }
      return s;
   }
   }
}
