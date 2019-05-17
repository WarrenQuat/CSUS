/* The TeamLinkedListClient class
   Quattrocchi, Warren
*/
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TeamLinkedListClient
{
  public static void main( String [] args ) 
  {
    //construct TeamLinkedList
    //read the file and add each team to the list
    TeamLinkedList teams = new TeamLinkedList( );
    Scanner input = new Scanner(System.in);
    boolean isFound;
    String answer;
    do
    {
      //loop until file is found
      isFound = true;
      try{
      populate(input, teams);
      }catch(IOException a){
      System.err.println(a.getMessage());
      isFound = false;
    }
    }while (!isFound);
    
    //infinte loop until user quits
    for(;;)
    {
      //display menu
      displayMenu();
      //input answer
      answer = input.next();
      //ignore case in user input
      answer = answer.toLowerCase();
      //switch statement for menu input
      switch (answer)
      {
         //on case delete, print error message if exception is thrown
         case "delete":  
                         try{      
                           deleteTeam(teams, input);
                         }catch(DataStructureException e){ 
                           System.err.println(e.getMessage());
                         }
         break;
         case "return":  
                         try{
                            returnFirstTeam(teams);
                         }catch (DataStructureException e){
                            System.err.println(e.getMessage());
                         }   
         break;
         case "winnest": mostWins(teams);
         break;
         case "fivebest":fiveBest(teams);
         break;
         case "order":   orderTeams(teams);
         break;
         case "add":     addTeam(teams,input);
         break;
         case "display": displayTeams(teams);
         break;
         case "reverse": reverst(teams);
         break;
         case "quit":    System.exit(0);
         break;
         //on case search print error message if exception is thrown
         case "search":  
                           try{
                             search(teams, input);
                           }catch (DataStructureException e){
                             System.err.println(e.getMessage());
                           }
         break;
         default:        System.out.println("Invalid input");
      }   
   }
 }
  //delete team method
  public static void deleteTeam(TeamLinkedList team, Scanner input) throws DataStructureException
  {
   input.nextLine();
   System.out.println("Enter a team name to delete");
   String name = input.nextLine();
   //call delete method in teamlinkedlist
   Team t =team.delete(name);
   System.out.println(t.getNickname() + " have been deleted");
  }  
  //return first team method
  public static void returnFirstTeam (TeamLinkedList team) throws DataStructureException
  {
   //call returnfirst method in teamlinkedlist
   Team t = team.returnFirst();
   System.out.println("The first team in the list is: ");
   System.out.println (t);
  }
  //fivebest method
  public static void fiveBest(TeamLinkedList teams)
  {
   //create a new arraylist of teams and initialize it with the fivebestteams method from teamlinkedlist
   ArrayList<Team> best = teams.fiveBestTeams();
   if(best.isEmpty())
      System.out.println("No teams in list");
   else{
   String s = "";
   //print out all teams in list
   for (int i = 0; i < best.size(); i++)
   { 
      Team t = best.get(i);
      s = s + t.toString();
   }
   System.out.println(s);
  }
  }
  //return the teams with the most wins
  public static void mostWins(TeamLinkedList teams)
  {
    ArrayList<String> winnest = teams.mostWins();
    if(winnest.isEmpty())
      System.out.println("No teams in list");
    else{
    System.out.println("The team(s) with the most wins are: ");
    System.out.println(winnest);
    }
  }
  
  //populate the linkedlist with the file chosen by user  
  public static void populate(Scanner input, TeamLinkedList teams)throws IOException
  {
    Team f;
    System.out.println("Enter the file name: ");
    String fileName = input.nextLine();
    File teamFile = new File(fileName);
    Scanner teamInput = new Scanner(teamFile);
    while(teamInput.hasNextLine())
    {
      String teamName = teamInput.next();
      int wins = teamInput.nextInt();
      int losses = teamInput.nextInt();
      f = new Team(teamName, wins, losses);
      teams.insert(f);
    }
  }
  
  //add a team to the linkedlist
  public static void addTeam(TeamLinkedList teams, Scanner input)
  {
   input.nextLine();
   System.out.println("Please enter a team name to add: ");
   String name = input.nextLine();
   System.out.println("Enter the number of wins: ");
   int wins = input.nextInt();
   System.out.println("Enter the number of losses: ");
   int losses = input.nextInt();
   Team t = new Team(name,wins,losses);
   teams.insert (t);
  }
  
  //display all teams in the list
  public static void displayTeams(TeamLinkedList teams)
  {
    System.out.println(teams);
  }
  
  //order the list
  public static void orderTeams(TeamLinkedList teams)
  {
   ArrayList<Team> ordered = teams.orderTeams();
   if(ordered.isEmpty())
      System.out.println("No teams in list");
   else{
   String s = "";
   for (int i = 0; i < ordered.size(); i++)
   { 
      Team t = ordered.get(i);
      s = s + t.toString();
    }
    System.out.println(s);
  }
  }
  
  //search through the list for a specific team
  public static void search(TeamLinkedList teams, Scanner input) throws DataStructureException
  {
   input.nextLine();
   System.out.println("Enter a team name: ");
   String name = input.nextLine();
   Team t = teams.peek(name);
   System.out.println(t);
  }
  
  //display the menu
  public static void displayMenu()
  {
   System.out.println("\n");
   System.out.println("Enter a selection");
   System.out.println("delete----->Delete a Team");
   System.out.println("return----->Return first team");
   System.out.println("order------>Order the teams");
   System.out.println("add-------->Add a team");
   System.out.println("display---->Display teams");
   System.out.println("fivebest--->Display 5 best teams");
   System.out.println("winnest---->Display teams with the most wins");
   System.out.println("reverse---->Reverse list");
   System.out.println("search----->Search for a team");
   System.out.println("quit------->quit\n");
  } 
    //his method receives the link list of the teams and prints the list in the reverse order.
    //you can use a stack or array or array list to print the link list in the reverse order
    public static void reverst(Object o)
    {
      if (o instanceof TeamLinkedList)
       {
        TeamLinkedList teams = (TeamLinkedList)o;
        if (teams.isEmpty())
         System.out.println("No teams in list");
        else{
        ArrayList<Team> reversed = teams.reverseList();
        String s = "";
        for (int i = 0; i < reversed.size(); i++)
        { 
           Team t = reversed.get(i);
           s = s + t.toString();
        }
         System.out.println(s);
        }
       }else{
       System.out.println("Not a team object");
       }
    }
}
