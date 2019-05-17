// Class FamilyInfo
// This class can be used to store basic family relationship information
// about a particular family.  

import java.util.*;
import java.io.*;

public class FamilyInfo {

    //call the instance variable  people
     private ArrayList<Person> people;

    private final String LIST_END = "END";  // signals end of list

    //  constructs an empty family list/instantiates the list
    public FamilyInfo() 
    {
      people = new ArrayList<Person>();  
    }

    // returns the position of a name in the list of people, 
    //       -1 if not found
    private int indexOf(String name)
    {
        for(int i = 0; i < people.size(); i++) {
            if(people.get(i).getName().equals(name))
            {
              return i;
            }
           //if the name at index i is equals to the name
               //return the index i
           }   
        return -1;
     }

    //  returns a reference to the person with the given name if
    //       they are in the list; returns null if not found
    public Person getPerson(String name)
    {
       for(int i = 0; i < people.size(); i++) {
           if(people.get(i).getName().equals(name))
            {
          return people.get(i);
            }
        }
        return null;
     }

    // pre : input contains a sequence of birth records and all people
    //       have already been read into people list
    // post: reads birth records, updating mother/father/children info
    private void processParents(Scanner input)
    {
       //declare string varaibles for the personName, fatherName, motherName
       String personName;
       String fatherName;
       String motherName;
        //declare 3 Person's object for called person , mother and father
        Person mother;
        Person father;
        Person person;
         
        for(int i = 0;i < people.size();i++) 
        {
            //read the name from the file and store it in personName
            personName = input.nextLine();
            //if the name is "END"
            if(personName.equals(LIST_END))
            {
               //break
               return;
            }   
            // call the method getPerson from the person class and store the result in person variable 
             person = getPerson(personName);
            //read another name from the file and store it in motherName
             motherName = input.nextLine();
            //read another name from the list and store it in fatherName
             fatherName = input.nextLine();
            //***********************
            //if the motherNmae is not equal to unknown
             if(!motherName.equals("unknown"))
               {
                 //call the method getPerson(motherNmae) and store the result in mother
                 mother = getPerson(motherName);
                 //call the method setMother(mother) on the object person
                 person.setMother(mother);
                 //call the method addKid(person) on the mother object
                 mother.addKid(person);
               } 
                
              //repeat for father 
              if(!fatherName.equals("unknown"))
               {       
                  father = getPerson(fatherName);
                  person.setFather(father);
                  father.addKid(person);
               }    
               
          }          
    }

    //input file  is open for reading and contains a legal family list
    //the first sets of the names in the list will be read and the array list is created
    //input is the pointer to the file containing the names
    public void read(Scanner input) throws FileNotFoundException
    {
         //read the first name in the file
         String name = input.nextLine();
         //while (name is not equals to "END) {
         while(!name.equals(LIST_END))
         {
            //create an object of person
            Person e = new Person(name);
            //add the object to the list 
            people.add(e);
            //read another name from the list
            name = input.nextLine();
         
         }
          //call the method processParents 
          processParents(input); 
    }                                                    
}
