import java.util.*;

public class InvestorDriver
{

   
   public InvestorDriver()
   {
   }
   
   //buy a stock
   static void buyStock(Investor me, java.util.Scanner kb)
   {
      java.lang.String name;
      int amount;
      double price;
      
      do{
         System.out.println ("Enter the name of the stock you are purchasing: ");
         name = kb.next();
         System.out.println ("Enter the number of the stock that you are purchasing: ");
         amount = kb.nextInt();
         System.out.println ("Enter the price of the stock you are purchasing: ");
         price = kb.nextDouble();
         
        if (name.equals(null) || price <= 0 || amount <=0)                    //ignore invalid input
        {
          System.out.println("Invalid entry");
        }
        
      }while(name.equals(null) || price <= 0 || amount <=0);                  //loop when invalid input is detected
      
      me.buy(name, amount, price);                                            //call buy method in investor to create a stock object
      kb.nextLine();
   }
   
   
   //display all stocks
   static void display (Investor me)
   {
     Stock[] stocks;                    
     stocks = me.getStock();                                                   //retrieve array of curently owned stocks
     int count = me.getCount();                                                //get amount of stocks curently owned
     if (count == 0)                                                           //display to user that no stocks are currently owned if count is equal to 0
     {
      System.out.println("You do not currently own any stocks \n");
     }
     for(int i = 0; i < count; i++)                                            //loop through array and display all stocks
     {
        if(stocks[i].getAmount() > 0)                                          //ignore stocks that have been completely sold
        {
         System.out.println(stocks[i].toString() + "\n");                      //print the stock details
        }
     }  
   }
   
   
   //display the menu
   static void displayMenu(java.util.Scanner kb)                               //prompt user with a list of choices
   {
      System.out.println("\n");
      System.out.println("Choose one of the following options");
      System.out.println("***********************************");
      System.out.println("--> Buy");
      System.out.println("--> Sell all");
      System.out.println("--> Sell partial");
      System.out.println("--> Display");
      System.out.println("--> Gain or loss");
      System.out.println("--> Exit");
      System.out.println("***********************************");
      System.out.println("\n");     
   }
   
   
   //sell all stocks
   static void sellStock(Investor me, java.util.Scanner kb)
   {
      java.lang.String name;
      double price;
      
      do
      {  
         display(me);                                                             //display all stocks to user before prompting for name
         System.out.println("Enter the name of the stock you are selling: ");
         name = kb.next();
         System.out.println("Enter the current price of the stock you are selling: ");
         price = kb.nextDouble();
         
         if (name.equals(null)|| price <= 0 || me.find(name)== -1)                //ignore invalid input
         {
           System.out.println("Invalid entry");
         }
         
      }while (name.equals(null)|| price <= 0 || me.find(name)== -1);              //loop when invalid input is detected
      double total = me.sellAll(name, price);                                     //call sellAll method from Investor class
      System.out.println("The amount deposited to your account is: " + total);
      kb.nextLine();
   }
   
    //sell select amount of stocks
    static void sellPartial(Investor me, java.util.Scanner kb)
   {
      java.lang.String name;
      double price;
      int amount;
      double total;
      
      do
      {
         display(me);                                                              //display all stocks to user before prompting for name
         System.out.println("Enter the name of the stock you are selling: ");
         name = kb.next();
         System.out.println("Enter the current price for the stock you are selling: ");
         price = kb.nextDouble();
         System.out.println("Enter the number of the stocks you want to sell: ");
         amount = kb.nextInt();
                  
         if (name.equals(null)|| price <= 0 || amount <=0 || me.find(name)== -1 )   //ignore invalid input
         {
           System.out.println("Invalid entry");
         }
         
      }while (name.equals(null) || price <= 0 || amount <= 0|| me.find(name)== -1); //loop when invalid input is entered
      total = me.sellPartial(name, price,amount);                                   //call sellPartial method from Investor
      if(total == -1)
      {
         System.out.println("you do not have enought stocks");
      }
      else{
         System.out.println("The amount deposited to your account is: " + total);
      }
      
      kb.nextLine();
   }
   
   //check for a gain or loss
   static void gainOrLoss(Investor me, java.util.Scanner kb)
   {
      Stock[] stocks;
      stocks = me.getStock();
      java.lang.String name;
      double currentPrice;
      double total;
      do
      {    
         display(me);                                                               //display all stocks to user before prompting for name
         System.out.println("Select the stock you want to find out the gain or loss: ");
         name = kb.next();
         System.out.println("Enter the current price for this stock: ");
         currentPrice = kb.nextInt();
         
         if (name.equals(null)|| currentPrice <= 0 || me.find(name)== -1)           //ignore invalid input
         {
           System.out.println("Invalid entry");
         }
         
      }while (name.equals(null)|| currentPrice <= 0 || me.find(name)== -1);         //loop when invalid input is entered
      
      int i = me.find(name);                                                        //search through stock array to find name by calling the find method from Investor
      total = (stocks[i].getProfit(currentPrice));                                  //get total amount gained or lost
      kb.nextLine();
      
      if(total >0)                                                                  //Gain
      {
         System.out.println("Gain: " + total);
      }
      else if(total == 0)                                                           //Break even
      {
         System.out.println("No gain or loss");
      }
      else                                                                          //Loss
      { 
         System.out.println("Loss: " + total * -1);
      }
       
   }
   
    
    //main
    public static void main(String[] args)
    {  
      java.lang.String answer;
      java.util.Scanner scan = new Scanner(System.in);
      System.out.println("Enter your name: ");                                     //get investor/user name
      java.lang.String name = scan.nextLine();
      Investor invest = new Investor(name);                                        //create new investor object using user chosen name
      
      do
       {
         displayMenu(scan);                                                        //display menu and prompt for an option                       
         answer = scan.nextLine();
         answer = answer.toLowerCase();                                            //make input not case-sensitive
         
         switch(answer)                                       
         {
            case "buy" :         buyStock(invest,scan);                            //buy a stock
                   break;
            case "sell all":     sellStock (invest, scan);                         //sell all of a stock
                   break;
            case "sell partial": sellPartial(invest, scan);                        //sell a select number of a stock
                   break;
            case "display":      display(invest);                                  //display all stocks
                   break;
            case "gain or loss": gainOrLoss(invest,scan);                      //calculate gain or loss
                   break;  
            case "exit":  
                   break;    
            default:             System.out.println("Invalid input");              
          } 
          
        } while (!answer.equals("exit"));                                      //end program on user input of "exit"
    }
}