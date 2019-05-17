// Class FamilyTest
// This program tests the FamilyInfo and Person classes by reading a
// file with family information and showing the maternal line, paternal
// line and children for various people.

import java.io.*;
import java.util.*;

public class FamilyTest {
    public static void main (String[] args) throws FileNotFoundException
    {
        //call the method giveIntro
        giveIntro();
        //cretae an object of FamilyInfo
        FamilyInfo list = new FamilyInfo();
        //create a Scaaner object
        Scanner scan = new Scanner(System.in);
        //get the file name
        System.out.println("What is the input file?: ");
        String fileName = scan.nextLine();
        //cretae a Scanner object for the file name
        Scanner input = new Scanner(new File(fileName)); 
        list.read(input);
        doMatches(scan,list);
         
    }
   //Gives user information on program purpose
    public static void giveIntro()
    {
        System.out.println("This program reads an input file with family\ninformationand provides information about the\nmaternal line, paternal line and children of\nvarious people.\n");
    }

    public static void doMatches(Scanner console, FamilyInfo list)
    {    
        //infinite loop, only ends when quit is entered
        for(;;) 
        {
             System.out.println("Person's name ('quit' to end): ");
             String name = console.nextLine();
               //quit program on name == quit
               if(name.equals("quit"))
               {                 
                  System.exit(0);
               }
             //creata a person object
             Person next;
             //find person in created objects
             next = list.getPerson(name);
               //if no object with that name is found, prompt user with no match found
               if (next == null)
               {
                  System.out.println("No match found");
               }
               else{
                  //call methods to show relatives using person entered
                  showMaternal(next);
                  showPaternal(next);
                  showChildren(next);
               }
          }
       }
    
    // Shows maternal ancestors for given person
    public static void showMaternal(Person current)
    {
      //print maternal line and original persons name
      System.out.println("\nMaternal line:");
      System.out.println(current.getName());
      
      //loop until no more mothers are found
      while(current.getMother() != null)
      {
         //put mother into the person object
         current = current.getMother();
         //print name of mother
         System.out.println(current.getName());
      }
      System.out.println("\n");
     }
     
    // Shows paternal ancestors for given person. 
    public static void showPaternal(Person current)
    {
      //print paternal line and original persons name
      System.out.println("Paternal line:");
      System.out.println(current.getName());
      
      //loop until no more mothers are found
      while(current.getFather() != null)
      {
         //put father into the person object
         current = current.getFather();
         //print name of father
         System.out.println(current.getName());
      }
      System.out.println("\n");
    }

    // Shows children for given person
    public static void showChildren(Person current) 
    {
            //create a person object name kids
            Person kids;
            System.out.println("Children:");
            //if number of kids == 0, print none
            if(current.numKids() == 0)
            {
               System.out.println("None");
         
            }else{
              //for loop until number of kids is reached
              for (int i = 0; i < current.numKids(); i++) 
              {
                //get kid[i]
                kids = current.nthKid(i);
                //print kids name
                System.out.println(kids.getName());
              }
            System.out.println("\n");
            } 
     }
}
