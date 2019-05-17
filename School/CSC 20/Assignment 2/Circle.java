//Warren Quattrocchi
//CSC 20 Tu/Th 8:00 - 8:50 am

//Circle class
//Subclass of Geometric Object
//Implements calculate interface
public class Circle extends GeometricObject implements Calculate
{
   //circle variables 
   private double radius;
   
      //default constructor
      public Circle()
      {
      super();
      radius = 1;
      }
      
      //constructor if only radius specified
      public Circle(double radius)
      {
         super();
         this.radius = radius;
      }
      
      //contructor if all fields specified
      public Circle(double radius, java.lang.String color, boolean filled)
      {
         super(color, filled);
         this.radius = radius; 
      }
      
      //return radius
      public double getRadius()
      {
         return radius;
      }
      
      //set radius
      public void setRadius(double radius)
      {  
        //do not set negative or 0 radius
        if(radius > 0)
        {
         this.radius = radius;
        }
      }
      
      //get diameter of circle
      public double getDiameter()
      {
         return radius * 2;
      }
      
      //get area
      public double getArea()
      {
         return (java.lang.Math.PI * (radius * radius));
      }
      
      //get perimeter
      public double getPerimeter()
      {
         return (2 * java.lang.Math.PI * radius);
      }
      
      //equals method
      public boolean equals(Object other)
      {
         return (this == other);
      }
      
      //toString method, return information on object
      public java.lang.String toString()
      {
         return  "Shape: Circle " + "\n" +
                 "Radius: " + radius + "\n" +
                 super.toString();
      }
}