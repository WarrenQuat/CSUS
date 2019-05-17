/*Warren Quattrocchi
  Justin Roldan
  CSC 20
  Library class
*/


import java.io.*;
import java.util.*;

public class Library implements Serializable
{
   LinkedList<Book> books;
   String name;
   
   public Library(String name)
   {
      this.name = name;
      books = new LinkedList<Book>();
   }
   
   public int getSize()
   {
      return books.size();
   }
   
   public void add(Book b)
   {
      books.add(b);
   }
   
   public void setBooks(LinkedList<Book> books)
   {
      this.books = books;
   }
   
   public LinkedList<Book> returnBooks()
   {
      return books;
   }
   public void removeBook(String bookDetails)
   {
      for(int i = 0; i< books.size(); i++)
      {
         if(bookDetails.equals(books.get(i).toString()))
         {
            books.remove(i);
         }
       }
    }
   public ArrayList<Book> searchGenre(String genre)
   {
         sortBooks();
         ArrayList<Book> searchGenre = new ArrayList<Book>();
         for(int i = 0; i < books.size(); i ++)
         {
            if (books.get(i).getGenre().toLowerCase().equals(genre.toLowerCase()))
               searchGenre.add(books.get(i));
         }
           return searchGenre;
   }
   
   public ArrayList<Book> searchAuthor(String author)
   {
         sortBooks();
         ArrayList<Book> searchAuthor = new ArrayList<Book>();
         for(int i = 0; i < books.size(); i ++)
         {
            if (books.get(i).getAuthor().toLowerCase().equals(author.toLowerCase()))
               searchAuthor.add(books.get(i));
         }
           return searchAuthor;
   }
   
   public ArrayList<Book> searchKeyword(String word)
   {
         sortBooks();
         ArrayList<Book> searchKey = new ArrayList<Book>();
         for(int i = 0; i < books.size(); i ++)
         {
            if (books.get(i).getName().toLowerCase().contains(word.toLowerCase()) || books.get(i).getGenre().toLowerCase().contains(word.toLowerCase()))
               searchKey.add(books.get(i));
         }
           return searchKey;
   }   
   public void sortBooks()
   {
      for (int i = 0; i< books.size(); i++)
      {
         for(int j = i+1; j<books.size(); j++)
         {
            if(books.get(i).compareTo(books.get(j)) > 0)
            {
               Book temp = books.get(j);
               books.set(j, books.get(i));
               books.set(i,temp);
            }
         }   
        
      }    
   }
}