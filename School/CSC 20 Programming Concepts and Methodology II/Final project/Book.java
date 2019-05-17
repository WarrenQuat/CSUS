/*Warren Quattrocchi
  Justin Roldan
  CSC 20
  Book class
*/


import java.io.*;

public class Book implements Serializable, Comparable
{
   private String name;
   private String author;
   private String genre;
   private String date;
   private boolean checkedOut;
   
   public Book (String name, String author, String genre, String date)
   {
      this.name = name;
      this.author = author;
      this.genre = genre;
      this.date = date;
      checkedOut = false;
   }
   
   public String getName()
   {
      return name;
   }
   
   public String getAuthor()
   {
      return author;
   }
   
   public String getGenre()
   {
      return genre;
   }
   public void setName(String name)
   {
      this.name = name;
   }
   
   public void setAuthor(String author)
   {
      this.author = author;
   }
   
   public void setGenre(String genre)
   {
      this.genre = genre;
   }
   
   public boolean isCheckedOut()
   {
      return checkedOut;
   }
   public int compareTo(Object o)
   {
      if (o instanceof Book)
      {
         Book other = (Book) o;
         return this.getName().compareTo(other.getName());
      }
      return -1;
   }  
   
   public boolean equals(Object o)
   {
      return (this == o);
   }
   
   public String toString()
   {
      String s =String.format("Title: %-40s Genre: %-10s Author: %-20s",name,genre,author);
      return s;
   }
}