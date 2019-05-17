import java.util.*;

public class Investor
{
   //declare global variables
   private int count = 0;
   private static int id;
   private Stock[] myStocks = new Stock[10];
   private java.lang.String name;
   
   //constructor
   public Investor(java.lang.String name)
   {
      this.name = name;
   }
   
   //buy a stock
   public void buy (java.lang.String name, int amount, double price)
   {
         
       myStocks[count] = new Stock (amount, name, price);                           //create a new stock with user inputs from investorDriver
       System.out.println("Here is the info about the stock that you purchased: ");
       System.out.println(myStocks[count].toString());                              //print purchased stock info
       count++;                                                                     //increment count of current stocks by 1
   }
   
   //search for name match in stock array
   public int find (java.lang.String name)                                          //search through stock array for a matching name
   {  
      for (int i = 0; i < count; i++)                                               
      {
         if (myStocks[i].getName().equals(name))                                    //if a match is found return the index
         {
           return i;
         }
      }
      System.out.println("Name not found");                                         //tell user that no match was found/invalid name was entered
      return -1;                                                                    //if no match is found return -1
   }
   
   //get current stock count
   public int getCount()
   {
      return count;                                                                 //get current count of a particular stock
   }
   
   //return stock array
   public Stock[] getStock()
   {
      return myStocks;                                                              //return all stocks
   }
   
   //sell all of a stock
   public double sellAll(java.lang.String name, double currentPrice)
   {
      if(name != null || currentPrice >0)
      {

        int index =  find(name);                                                    //search for user inputted name by calling the find method
        double total = myStocks[index].sellAll(currentPrice);                       //get money earned from selling the stock
        return total;                                                               //return total money earned
      }
      else {
         return -1;
      }
   }
   
   //sell a user determined amount of a stock
   public double sellPartial(java.lang.String name, double currentPrice, int amount)
   {
       java.util.Date date = new Date(); 
       int index =  find(name);                                                     //search for user inputted name by calling the find method
       if(myStocks[index].getAmount() > 0 && myStocks[index].getAmount() - amount >0)
       {
         double total = myStocks[index].sell(amount, currentPrice);                 //get money earned from selling the amount of stock specified
         myStocks[index].setDateSold(date);                                         //create a sale date
         return total;                                                              //return total money earned
       }
       else {
         return -1;
       }
   }

   //toString (display investor object info)
   public java.lang.String toString()
   {
        return "Name: " + this.name + "\n" +
               "ID  : " + this.id   + "\n" +
               "Stock count: " + this.count;
   }
 }