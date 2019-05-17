import java.util.*;

public class Stock{

private int amount;
private java.util.Date datePurchased;
private java.util.Date dateSold;
private java.lang.String name;
private double price;
   
   //Contructor
   public Stock(int amount, java.lang.String name, double price)
   {
     this.amount = amount;
     this.name = name;
     this.price = price;
     this.datePurchased = new Date();
   }
   
   //set amount of stock
   public void setAmount(int amount)
   { 
     if (amount >= 0)                                                              
     {
      this.amount = amount;
     }   
   } 
   
   //set name of stock
   public void setName(java.lang.String name)
   {
   
      this.name = name;
   }
   
   //set date of sold stock, envoke within sell methods
   public void setDateSold(java.util.Date dateSold)
   {
      this.dateSold = new Date();
    
   }   
   
   //set price of stock
   public void setPrice(double price)
   {
     if(price >0){
       this.price = price;
       }
        
   }    
    
   //get name of stock 
   public java.lang.String getName ()
   {
      return name;  
   } 
 
   //get stock price
   public double getPrice()
   {
      return price;
   }
   
   //get total profit of sale 
   public double getProfit(double currentPrice)
   {
      return (currentPrice - price) * amount;   
   }
    
    //get stock amount  
    public int getAmount()
    {
      return amount;
    } 
    
    //price change difference
    public double difference (double currentPrice)
    {
      return price - currentPrice;  
    }
    
   //sell  
   public double sell(int count, double currentPrice)
   {
      System.out.println("This is the info about the stock you are selling:");
      System.out.println(toString());
      double total = currentPrice * count;
      this.amount = amount - count;
      System.out.println("Here is your modified stock:");
      System.out.println(toString());
      setDateSold(dateSold);
      return total;
   }  
   
    //check if object is equal
    public boolean equals(java.lang.Object other)
    {
      return (this == other);
    }
   
    //sell all stock 
    public double sellAll(double currentPrice)
   {
      
      System.out.println("This is the info about the stock you are selling:");
      System.out.println(toString());
      double total = currentPrice * this.amount;
      this.amount = 0;
      return total;
   }   
   
    //toString method (display stock object info)
    public java.lang.String toString() 
    {
      if (this.dateSold ==  null)
      {
         return 
             
             "Name:   "         + this.name          + "\n" +
             "Price:  "         + this.price         + "\n" +
             "Amount: "         + this.amount        + "\n" +
             "Date Purchased: " + this.datePurchased        ;   
      }  
      else
      {
         return 
             
             "Name:   "         + this.name          + "\n" +
             "Price:  "         + this.price         + "\n" +
             "Amount: "         + this.amount        + "\n" +
             "Date Purchased: " + this.datePurchased + "\n" +
             "Date Sold: "      + this.dateSold      + "\n";
      }
   }
}   
 