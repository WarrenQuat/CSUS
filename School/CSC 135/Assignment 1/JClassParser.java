/* CSC 135 Project 1
 * Simple java class parser
 * predictive recursive descent
 * Warren Quattrocchi
 */

import java.io.IOException;
import java.util.Scanner;

public class JClassParser 
{
	static String inputString;
	static int index = 0;
	static int errorflag = 0;
	private char token()
	{
		return (inputString.charAt(index));
	}
	
	private void advancePtr() 
	{
		if (index < (inputString.length() - 1)) index++;
	}
	
	private void match(char T) 
	{
		if (T == token()) advancePtr();
		else error();
	}
	
	private void error() 
	{
		System.out.println("error at position: " + index);
		errorflag = 1;
		advancePtr(); 
	} 
	
 private void jClass()
 { 
	 classname();
	 if(token() == 'X')
	 {
		 match('X');
	 	 classname();
	 }
	 match('B');
	 varlist();
	 match(';');
	 //FIRST(method)
	 while(token() == 'P'|| token() == 'V')
	 {
		 	method();
	 }
	 match('E');
 }
 
 private void classname()
 {
	if(token() == 'D')
		match('D');
	else if(token() == 'C')
		match('C');
	else
		 error();
 }
 
 private void varlist()
 {
	vardef();
	//match ',' and call vardef
	//while token == ','
	while(token() == ',')
	{
		match(',');
		vardef();
	}
 }
 private void vardef()
 {
	 //first statement for <type>
	 if(token() == 'I' || token() == 'S')
	 {
		 type();
		 varname();
	 }
	 //first statement for <classname>
	 else if(token() == 'C' || token() == 'D')
	 {
		 classname();
		 varref();
	 }else
		 error();
	 
 }
 
 private void type()
 {
	 if(token() == 'I')
			match('I');
	 else if(token() == 'S')
			match('S');
	 else
		 error();
 }
 
 private void varname()
 {
	 letter();
	 //first of <char>
	 while(token() == 'Y' || token() == 'Z' || 
		   token() == '0' || token() == '1' || token() == '2' || token() == '3')
	 {
		character(); 
	 }
 }
 
 private void character()
 {
	 //first(letter)
	 if(token() == 'Y' || token() == 'Z')
		 letter();
	 //first(digit)
	 else if(token() == '0' || token() == '1' || token() == '2' || token() == '3')
		 digit();
	 else
		 error();
 }
 
 private void method() 
 {
	 accessor();
	 type();
	 methodname();	
	 match('(');
	 
	 //first(varlist)
	 if(token() == 'I' || token() == 'S' || token() == 'C' || token() == 'D')
		 varlist();
	 match(')');
	 match('B');
	 
	 //first of <statement>
	 while(token() == 'F' || token() == 'Y'|| token() == 'Z' || token() == 'J' ||
		   token() == 'K'|| token() == 'W')
		 statemnt();
	 returnstatemnt();
	 match('E');
 }
 
 private void accessor()
 {
	 if(token() == 'P')
			match('P');
	 else if(token() == 'V')
			match('V');	
	 else
		 error();
 }
 
 private void methodname()
 {
	 if(token() == 'M')
			match('M');
	 else if(token() == 'N')
			match('N');
	 else
		 error();
 }
 
 private void statemnt()
 {
	 if(token() == 'F')
		 ifstatemnt();
	 
	 //first(assignstatemt)
	 else if(token() == 'Y' || token() == 'Z'|| token() == 'J'|| token() == 'K')
	 {
		 assignstatemnt();
		 match(';');
	 }
	 	
	 //first(whilestatemt)
	 else if(token() == 'W')
		 whilestatemnt();
	 else
		 error();
 }
 
 private void ifstatemnt()
 {
	 match('F');
	 cond();
	 match('T');
	 match('B');
	 
	 //first(statemt)
	 while(token() == 'F' || token() == 'Y'|| token() == 'Z' || token() == 'J' ||
			   token() == 'K'|| token() == 'W')
			 statemnt();
	 match('E');
	 
	 if(token() == 'L')
	 {
		 match('L');
		 match('B');
		 
		 //first(statemt)
		 while(token() == 'F' || token() == 'Y'|| token() == 'Z' || token() == 'J' ||
				   token() == 'K'|| token() == 'W')
				 statemnt();
		 match('E');
	 }
	
 }
 
 private void assignstatemnt()
 { 
	 if(token() == 'Y' || token() == 'Z') 
	 {
		 varname();
		 match('=');
		 mathexpr();
	 }
	 
	 //first(varref)
	 else if(token() == 'J' || token() == 'K')
	 {
		 varref();
		 match('=');
		 getvarref();
	 }
	 else
		 error();
 }
 
 private void mathexpr()
 {
	 factor();
	 while(token() == '+')
	 {
		 match('+');
		 factor();
	 }
 }
 
 private void factor()
 {
	 oprnd();
	 while(token() == '*')
	 {
		 match('*');
		 oprnd();
	 }
 }
 
 private void oprnd()
 {
	 
	//first(integer)
	if(token() == '0' || token() == '1' || token() == '2' || token() == '3')
		integer();
	
	//first(varname)
	else if(token() == 'Y' || token() == 'Z')
		varname();
	
	else if(token() == '(')
	{
		match('(');
		mathexpr();
		match(')');
	}
	
	//first(methodcall)
	else if(token() == 'J' || token() == 'K')
		methodcall();
	else
		error();
 }
	 
 private void getvarref()
 {
	 if(token() == 'O')
	 {
		 match('O');
		 classname();
		 match('(');
		 match(')');
	 }
	 
	 //first(methodcall)
	 else if(token() == 'J' || token() =='K')
	 	methodcall();
	 else
		 error();
	 	
	 	
 }
 
 private void whilestatemnt()
 {
	 match('W');
	 cond();
	 match('T');
	 match('B');
	 while(token() == 'F' || token() == 'Y'|| token() == 'Z' || token() == 'J' ||
			   token() == 'K'|| token() == 'W')
			 statemnt();
	 match('E');
 }
 
 private void cond()
 {
	 match('(');
	 oprnd();
	 operator();
	 oprnd();
	 match(')');
 }
 
 private void operator()
 {
	 if(token() == '<')
			match('<');
	 else if(token() == '=')
			match('=');
	 else if(token() == '>')
		 	match('>');
	 else if(token() == '!')
		 	match('!');
	 else
		 error();
 }
 
 private void returnstatemnt()
 {
	match('R');
	varname();
	match(';');
 }
 
 private void methodcall()
 {
	 varref();
	 match('.');
	 methodname();
	 match('(');
	 
	 //first(varlist)
	 if(token() == 'C' || token() == 'D' || token() == 'I' || token() == 'S')
		 varlist();
	 match(')');
 }
 
 private void letter()
 {
	 if(token() == 'Y')
			match('Y');
	 else if(token() == 'Z')
			match('Z');	
	 else
		 error();

 }
 
 private void digit()
 {
	 if(token() == '0')
			match('0');
	 else if(token() == '1')
			match('1');	
	 else if(token() == '2')
			match('2');	
	 else if(token() == '3')
			match('3');
	 else
		 error();
 }
 
 private void integer()
 {
	 digit();
	 
	 //first(digit)
	 while(token() == '0' || token() == '1' || token() == '2' || token() == '3')
		 digit();
 }
 
 private void varref()
 {
	 if(token() == 'J')
		 match('J');
	 else if(token() == 'K')
		 match('K');
	 else
		 error();

 }
 
 private void start()
 {
	 jClass();
	 match('$');
	 if(errorflag== 0)
		 System.out.println("legal."+ "\n");
	 else
		 System.out.println("errors found."+ "\n");
}
 
 public static void main(String[] args) throws IOException {
  JClassParser rec = new JClassParser();
  Scanner input = new Scanner(System.in);
  System.out.print("\n" + "enter an expression: ");
  inputString = input.nextLine();
  rec.start();
  input.close();
  
 }
}