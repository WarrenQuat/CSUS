//Warren Quattrocchi
//CSC 20 Tu/Th 8:00 - 8:50 am

//Rectangle class
//Subclass of Geometric Object
//Implements calculate interface
public class Rectangle extends GeometricObject implements Calculate
{
   private double width;
   private double height;
      
      //default contructor
      public Rectangle()
      {
      super();
      height = 1.0;
      width = 1.0;
      }
      
      //constructor if only width and height specified
      public Rectangle(double width, double height)
      {
         super();
         this.width = width;
         this.height = height;
      }
      
      //constructor if all fields specified
      public Rectangle(double width, double height, java.lang.String color, boolean filled)
      {
         super(color, filled);
         this.width = width;
         this.height = height;
      }
      
      //get width
      public double getWidth()
      {
         return width;
      }
      
      //set width
      public void setWidth(double width)
      {
        //only set width if greater than 0
        if(width > 0)
        {
         this.width = width;
        }
      }
      
      //get height
      public double getHeight()
      {
         return height;
      }
      
      //set height
      public void setHeight(double height)
      {
        if (height > 0)
        {
         this.height = height;
        }
      }
      
      //get area
      public double getArea()
      {
         return width * height;
      }
      
      //get perimeter
      public double getPerimeter()
      {
         return 2 * (width + height);
      }
      
      //equals method
      public boolean equals(Object other)
      {
         return (this == other);
      }
      
      //toString method, return information on object
      public java.lang.String toString()
      {
         return "Shape: Rectangle " + "\n" +
                "Height: " + height + "\n" +
                "Width : " + width +  "\n" +
                 super.toString();
      }
}