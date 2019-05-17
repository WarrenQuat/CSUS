/*Warren Quattrocchi
  Justin Roldan
  CSC 20
  students class
*/


import java.util.*;
import java.io.*;

public class Students implements Serializable
{
   //Person p;
   ArrayList<Student> students;
   Person currentUser;
   
   public Students()
   {
      students = new ArrayList<Student>();
   }
   
   public void add(Student s)
   {
      students.add(s);
   }
   
   public void remove(Student s)
   {
      for(int i = 0; i < students.size(); i++)
      {
         if(students.get(i).equals(s))
            students.remove(i);
      }
   }
   public ArrayList<Student> returnStudents()
   {
      return students;
   }
   
   public void setStudents(ArrayList<Student> students)
   {
      this.students = students;
   }
   
   public Student search(String id) throws NotFoundException
   {
   sortStudents();
   int l = 0;
   int r = students.size() - 1;
   while(l <= r)
   {
      int mid = (r+l)/2;
      if(students.get(mid).getPerson().getID().compareTo(id) == 0)
         return students.get(mid);
      if(students.get(mid).getPerson().getID().compareTo(id) < 0)
         l = mid + 1;
      else
         r = mid - 1;
    }
    throw new NotFoundException("Invalid ID"); 
   }
   
   //selection sort
   public void sortStudents()
   {
      for (int i = 0; i< students.size(); i++)
      {
         for(int j = i+1; j<students.size(); j++)
         {
            if(students.get(i).getPerson().compareTo(students.get(j).getPerson()) > 0)
            {
               Student temp = students.get(j);
               students.set(j, students.get(i));
               students.set(i,temp);
            }
         }     
       } 
    } 
 }