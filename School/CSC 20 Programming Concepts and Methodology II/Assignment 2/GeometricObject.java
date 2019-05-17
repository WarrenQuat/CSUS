//Warren Quattrocchi
//CSC 20 Tu/Th 8:00 - 8:50 am

import java.util.*;

public class GeometricObject
{
   //variables to be used among all objects
   private java.lang.String color;
   private boolean filled;
   private java.util.Date dateCreated;
      
      //default constructor
      public GeometricObject()
      {
         this.filled = false;
         this.color = "white";
         this.dateCreated = new Date();
      }
      
      //constructor to be called by subclasses when parameters include variables in GeometricObject
      public GeometricObject(java.lang.String color, boolean filled)
      {
         this.color = color;
         this.filled = filled;
         this.dateCreated = new Date();
      }
      
      //get color of object
      public java.lang.String getColor()
      {
        if (!color.equals(null))
        {
         return color;
        }
        else{
         return "no color";
        }
      }
      
      //set color of object
      public void setColor(java.lang.String color)
      {
         this.color = color;
      }
      
      //return whether object is filled or not
      public boolean isFilled()
      {
         return filled;
      }
      
      //set if object is filled or not
      public void setFilled(boolean filled)
      {
         this.filled = filled;
      }
     
     //get date created
      public java.util.Date getDateCreated()
      {
         return dateCreated;
      }
      
      //toString method, return details of objects
      public java.lang.String toString()
      {
         return "Color : " + color + "\n"
              + "Filled: "+ filled + "\n"
              + "Date Created: " + dateCreated + "\n";
      }
}
      