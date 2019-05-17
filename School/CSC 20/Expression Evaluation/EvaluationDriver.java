/*
   Warren Quattrocchi
   csc 20
   Expression Evaluation driver
*/

import java.util.StringTokenizer;
import java.util.Scanner;

class EvaluationDriver 
{
   public static void main (String[] args)
   {
      String answer;
      Scanner sc = new Scanner(System.in);
      do{
      //if an exception is caught, the expression is invalid
      try{    
         //input expression from user
         System.out.println("enter an infix expression: ");
         String s = sc.nextLine();
         //convert to postfix
         s = infixToPostfix(s);
         System.out.print("Postfix expression: " + s);
         //evaluate postfix expression
         Integer result = evaluatePostfix(s);
         System.out.println ("= " + result);
      }catch (InvalidExpException iee)
      {
         System.err.println(iee.getMessage());
      }catch (ArrayIndexOutOfBoundsException a)
      {
         System.err.println(" is an Invalid Expression");
      }
      
      System.out.println("Press any key to continue (q to quit)");
      answer = sc.nextLine();
      }while(!answer.toLowerCase().equals("q"));
   }
   
   //Convert a given infix to postfix
   public static String infixToPostfix(String s) throws InvalidExpException
   {
      //create a new stack object of type String
      StackClass<String> stack = new StackClass<>();
      String result = "";
      StringTokenizer st = new StringTokenizer(s,"*()+/-",true);
      //while the tokenizer has more elements
      while(st.hasMoreElements())
      {
         //set current = next token
         String current = st.nextToken();
         //if the current token is a digit, add it directly to result
         if(isDigit(current))
            result = result + current + " ";
         //if it is '(', push it to stack
         else if (current.equals("("))
            stack.push(current);
         //if it is ')', check to make sure a '(' is present on the stack
         //and pop until empty
         else if (current.equals(")"))
         {
            while(!stack.isEmpty() && !stack.peek().equals("("))
               result = result + stack.pop() + " ";
               
            //throw invalidexpexception if not found
            if(!stack.isEmpty() && !stack.peek().equals("("))
            {
                 throw new InvalidExpException("Invalid Expression");           
            }
            else
               stack.pop();
         }
 
         else{
            //String next = stack.peek();
            //while the precedence of current is greater than or equal to the top of the stack
            //add top to result
            while(!stack.isEmpty() && precedence(current) <= precedence(stack.peek()))
               result = result + stack.pop() + " ";
            //push current token to stack   
            stack.push(current);
         }
      } 
      //pop and add to result until stack is empty
      while(!stack.isEmpty())
         result = result + stack.pop() + " ";
      //return new postfix string
      return result;  
   }
   
   //evaluate the postfix expression
   public static Integer evaluatePostfix(String s) throws ArrayIndexOutOfBoundsException
   {
      //create new tokenizer and stack of type Integer
      StringTokenizer st = new StringTokenizer(s," ",false);
      StackClass<Integer> stack = new StackClass<>();
      
      //while the tokenizer has more elements
      while(st.hasMoreElements())
      {
         String current = st.nextToken();
         //if the current token is a digit
         if(isDigit(current))
         {
            //parse as integer and push to stack
            Integer i = Integer.parseInt(current);
            stack.push(i);
         }
         else{
             //pop 2 operators from stack for arithmetic
             Integer op2 = (Integer)stack.pop();
             Integer op1 = (Integer)stack.pop();
            
            //if statements for what to do for each operator
            if(current.equals("*"))
               stack.push(op1 * op2);
            if(current.equals("/"))
               stack.push(op1/op2);
            if(current.equals("-"))
               stack.push(op1 - op2);
            if(current.equals("+"))
               stack.push(op1 + op2);
               }
             } 
      //return the final item on the stack, which will be the result       
      return (Integer) stack.pop();

   }
  
   //method for determining precedence
   public static int precedence(Object o)
   {
     String ch = (String)o;
     switch (ch)
     {
     case "+":
     case "-":
          return 1;
      
     case "*":
     case "/":
          return 2;
      
     case "^":
          return 3;
     }
     return -1;
    }
   
   //method for determining if a string is a digit 
   public static boolean isDigit(String current)
   {
      //attempt to parse the string to a digit
      //if an exception is caught, it will return false
      try{
         Integer d = Integer.parseInt(current);
      }catch(NumberFormatException nfe)
      {
         return false;
      }
      return true; 
   }
}