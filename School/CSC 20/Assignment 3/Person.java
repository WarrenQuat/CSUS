// Class Person
// This class can be used to store the family information for one person

import java.util.*;
import java.lang.String;

public class Person {
   
   private Person mother;
   private String name;
   private Person father;

   ArrayList<Person> kids;
     

    //ArrayList lids will be instantiated.*/
    public Person(String name) {
      this.name = name;
      this.mother = null;
      this.father = null;
      kids = new ArrayList<Person>();
    }

    // post : returns the name of the person
    public String getName() {
        return name;
    }

    // post : returns a reference to the mother of the person
    public Person getMother() {
  
        return mother;
  
    }

    // post : returns a reference to the father of the person
    public Person getFather() {

        return father;
 
    }

    // post : returns the number of children of the person
    public int numKids() {
        return kids.size();
    }

    // pre : 0 <= n < numKids
    // post: returns the nth child of this person (n = 0, 1, 2, 3, ...)
    public Person nthKid(int n) {
        return kids.get(n);
    }

    // post: sets the mother of this person
    public void setMother(Person mother) {
         this.mother = mother;
    }

    // post: sets the father of this person
    public void setFather(Person father) {
         this.father = father;
    }

    // post: adds the given kid as a child of this person
    public void addKid(Person kid) {
    if(kid != null)
         kids.add(kid);
    }
    
    public String toString()
    {
      String s = "";
      s = s + name + "\n" + " Mother: " + this.getMother() +"\n" + "Father: " + this.getFather() + "\n";
      for(int i = 0; i<numKids();i++)
      {
         s = s + kids.get(i).getName() + ", ";
      }
      s = s + "\n";
      return s;
  }
}