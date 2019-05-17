//Warren Quattrocchi
//CSC 20 Tu/Th 8:00 - 8:50 am

import java.util.*;

public class ObjectDriver
{
   //main
   public static void main(String args[])
   {
      java.lang.String answer;
      Scanner kb = new Scanner(System.in);
      Object[] objects = new Object[12];                                               //Create array of 12 objects
      
      fillArray(kb, objects);                                                          //call fill array method before menu
      
      do{                                                                              //loop menu after invalid input/ method call
      displayMenu();                                                                   //display menu and get input
      answer = kb.next();
      
      switch(answer)                                                                   //switch statement for menu input
      {
         case "area":      getArea(objects);                                           //get area when area is input
            break;
         case "perimeter": getPerimeter(objects);                                      //get perimter when perimeter is input
            break;
         case "output"   : output(objects);                                            //show output when output is input
            break;
         case "exit"     : 
            break;
         default:          System.out.println("Invalid input");                        //tell user invalid input was entered
       }
      }while(!answer.equals("exit"));                                                  //loop until exit
   }
    
   //display menu
   public static void displayMenu()                                                   
   {
      System.out.println("*************************************************");
      System.out.println("Area      -> Display the area of the shapes");
      System.out.println("Perimeter -> Display the perimeter of the shapes");
      System.out.println("Output    -> Display shape info");
      System.out.println("Exit      -> Close");
      System.out.println("*************************************************");
      System.out.println("Enter a selection: ");
   } 
    
   //display information on every shape created  
   public static void output(Object[] objects)                                        
   {
      for(int i = 0; i < objects.length; i++)
      {
            
         System.out.println(objects[i]);                                               //loop through object array and call tostring for each object
       }
   }    
   
   //get area 
   public static void getArea(Object[] objects)                                        
   {  
      for(int i = 0; i < objects.length; i++)                                          //loop through array and check for an instance of each shape
      {
            if(objects[i] instanceof Rectangle)
            {
               Rectangle c = (Rectangle)objects[i];                                    //cast object[i] to rectangle then get the width, height, and area by calling methods from the objects class
               System.out.println("The area of the rectangle with width and height of " +
                                 c.getWidth() + "," + 
                                 c.getHeight() + " is " + c.getArea());
            }
            
            if(objects[i] instanceof Circle)
            {
               Circle c = (Circle)objects[i];                                          //cast object[i] to circle then get the radius and area by calling methods from the objects class
               System.out.println("The area of the circle with radius " + 
                                  c.getRadius() + " is " + c.getArea());
            }
            if(objects[i] instanceof Triangle)
            {
               Triangle c = (Triangle)objects[i];                                      //cast object[i] to triangle then get side1, side2, side3, and area by calling methods from the objects class
               System.out.println("The area of the triangle with side 1, side 2 , and side 3 length of " + 
                                 c.getSide1() + "," + c.getSide2()+ "," +
                                 c.getSide3() + " is " + c.getArea());
            }
       }
   }
   
   //get perimeter
   public static void getPerimeter(Object[] objects)                               
   {
      for(int i = 0; i < objects.length; i++)
      {
            if(objects[i] instanceof Rectangle)
            {
               Rectangle c = (Rectangle)objects[i];                                    //cast object[i] to rectangle then get the width, height, and perimeter by called methods from the objects class
               System.out.println("The perimeter of the rectangle with width and height of " +
                                 c.getWidth() + "," + 
                                 c.getHeight() + " is " + c.getPerimeter());
            }
            if(objects[i] instanceof Circle)
            {
               Circle c = (Circle)objects[i];                                          //cast object[i] to circle then get the radius and perimeter by called methods from the objects class
               System.out.println("The perimeter of the circle with radius " + 
                                  c.getRadius() + " is " + c.getPerimeter());
            }
            if(objects[i] instanceof Triangle)
            {
               Triangle c = (Triangle)objects[i];                                      //cast object[i] to triangle then get side1, side2, side3, and perimeter by called methods from the objects class
               System.out.println("The perimeter of the triangle with side 1, side 2 , and side 3 length of " + 
                                 c.getSide1() + "," + c.getSide2()+ "," +
                                 c.getSide3() + " is " + c.getPerimeter());
            }
       } 
      
   }
   
   //fill array
   public static void fillArray(Scanner kb,Object[] objects)                          
   {
    java.lang.String objectType;  
    for(int i = 0; i < objects.length; i++)                                            //loop through array to fill
    { 
      //declare variables needed for each shape
      double height; 
      double width;
      double radius;
      double side1;
      double side2;
      double side3;
      boolean filled;
      java.lang.String color;
      do{
         System.out.println("Enter a shape for object " + (i + 1) + ": ");             //ask user for a shape for each object in array
         objectType = kb.next();                                                       
         objectType = objectType.toLowerCase();                                        //make string lowercase to ignore case
      }while (!objectType.equals("rectangle") && !objectType.equals("circle")          //loop is input is invalid
               && !objectType.equals("triangle"));
       
      
      if(objectType.equals("rectangle"))                                               //prompt user for dimensions, color, and fill of rectangle
      {
       do
       {
        System.out.println("Enter a width: ");
        height = kb.nextDouble();
        System.out.println("Enter a height: ");
        width = kb.nextDouble();
        System.out.println("Is this rectangle filled(true/false): ");
        filled = kb.nextBoolean();
        System.out.println("Enter the color of the rectangle: ");
        color  = kb.next();
        if(height <= 0 || width <= 0 || color.equals(null))                            //tell user if invalid input was entered
        {
         System.out.println("Invalid input");
        }
        }while(height <= 0 || width <= 0 || color.equals(null));                       //loop on invalid input entry

        objects[i] = new Rectangle(height, width, color, filled);                      //create a new rectangle
        System.out.println(objects[i]);  
      }
      
      if(objectType.equals("circle"))                                                  //promp user for radius, color, and fill of circle
      {
         do
         {
         System.out.println("Enter a radius: ");
         radius = kb.nextDouble();
         System.out.println("Is this circle filled(true/false): ");
         filled = kb.nextBoolean();
         System.out.println("Enter the color of the circle: ");
         color = kb.next();
         if (radius <= 0 || color.equals(null))
         {
            System.out.println("Invalid input");
         }
         }while (radius <= 0 || color.equals(null));
         objects[i] = new Circle(radius, color, filled);                               //create a new circle
         System.out.println(objects[i]);
      }
      
      if(objectType.equals("triangle"))                                                //prompt user for side lengths, color, and fill or triangle
      {
        do
        {
        System.out.println("Enter a length for side 1: ");
        side1 = kb.nextDouble();
        System.out.println("Enter a length for side 2: ");
        side2 = kb.nextDouble();
        System.out.println("Enter a length for side 3: ");
        side3 = kb.nextDouble();
        System.out.println("Is this triangle filled(true/false): ");
        filled = kb.nextBoolean();
        System.out.println("Enter the color of the triangle: ");
        color = kb.next();
        if (side1 <= 0 || side2 <= 0 || side3 <= 0 || color.equals(null))
        {
         System.out.println("Invalid input");
        }
        }while (side1 <= 0 || side2 <= 0 || side3 <= 0 || color.equals(null));
        objects[i] = new Triangle(side1,side2,side3,color,filled);                   //create a new triangle
        System.out.println(objects[i]);    
      }
    }
  } 
}