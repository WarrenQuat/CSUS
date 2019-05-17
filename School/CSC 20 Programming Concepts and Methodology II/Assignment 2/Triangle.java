//Warren Quattrocchi
//CSC 20 Tu/Th 8:00 - 8:50 am

//Triangle class
//Subclass of Geometric Object
//Implements calculate interface
public class Triangle extends GeometricObject implements Calculate
{
   //Triangle variable delcaration
   private double side1;
   private double side2;
   private double side3;
   
      //default constructor, all sides equal to 1.0 if lengths are not specified
      public Triangle()
      {
      super();
      side1 = 1.0;
      side2 = 1.0;
      side3 = 1.0;
      }
      
      //Constructor if no fill or color is specified
      public Triangle(double side1, double side2, double side3)
      {
         super();
         this.side1 = side1;
         this.side2 = side2;
         this.side3 = side3;
      }
      
      //Constructor for all variables related to triangle including fill and color from super class
       public Triangle(double side1, double side2, double side3,java.lang.String color, boolean filled)
      {
         super(color, filled);
         this.side1 = side1;
         this.side2 = side2;
         this.side3 = side3;
      }
      
      //get side 1 length
      public double getSide1()
      {
         return this.side1;
      }
      
      //get side 2 length
      public double getSide2()
      {
         return this.side2;
      }
      
      //get side 3 length
      public double getSide3()
      {
         return this.side3;
      }
      
      //set side 1 length
      public void setSide1(double side1)
      {
        //only set new length if greater than zero
        if (side1 > 0)
        {
         this.side1 = side1;
        }
      }
      
      //set side 2 length
      public void setSide2(double side2)
      {
        if (side2 > 0)
        {
         //only set new length if greater than zero
         this.side2 = side2;
        }
      }
      
      //set side 3 length
      public void setSide3(double side3)
      {
        if (side3 > 0)
        {
         //only set new length if greater than zero
         this.side3 = side3;
        }
      }
      
      //get area
      public double getArea()
      {
         double s = ((side1 + side2 + side3))/2;
         return Math.sqrt(s*(s-side1)*(s-side2)*(s-side3));
      }
      
      //get perimeter
      public double getPerimeter()
      {
         return side1 + side2 + side3;
      }
      
      //equals method
      public boolean equals(Object other)
      {
         return (this == other);
      }
      
      //toString, return information of object
      public java.lang.String toString()
      {
        return "Shape: Triangle " + "\n" +
               "Side 1: " + side1 + "\n" + 
               "side 2: " + side2 + "\n" +
               "side 3: " + side3 + "\n" +
                super.toString();
      }
}