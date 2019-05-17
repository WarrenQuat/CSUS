/*Warren Quattrocchi
  Justin Roldan
  CSC 20
  Administrator class
*/


import java.util.*;
import java.io.*;

public class Administrator implements Serializable
{
   Person p;
   ArrayList<Person> admins;
   
   public Administrator()
   {
      admins = new ArrayList<Person>();
   }
   
   public void add(Person p)
   {
      admins.add(p);
   }
   
   public Person getPerson()
   {
      return p;
   }
   
   public int getSize()
   {
      return admins.size();
   }
   
   public void setAdmins(ArrayList<Person> admins)
   {
      this.admins= admins;
   }
   
   public ArrayList<Person> returnAdmins()
   {
      return admins;
   }
   
   public Person search(String ID) throws NotFoundException
   {
   sortAdmins();
   int l = 0;
   int r = admins.size() - 1;
   while(l <= r)
   {
      int mid = (r+l)/2;
      if(admins.get(mid).getID().compareTo(ID) == 0)
         return admins.get(mid);
      if(admins.get(mid).getID().compareTo(ID) < 0)
         l = mid + 1;
      else
         r = mid - 1;
    }
    throw new NotFoundException ("Invalid ID");
    }
    
    public void sortAdmins()
    {
      for (int i = 0; i< admins.size(); i++)
      {
         for(int j = i+1; j<admins.size(); j++)
         {
            if(admins.get(i).compareTo(admins.get(j)) > 0)
            {
               Person temp = admins.get(j);
               admins.set(j, admins.get(i));
               admins.set(i,temp);
            }
         }    
      } 
   }
}   