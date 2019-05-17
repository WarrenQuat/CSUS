/*Warren Quattrocchi
CSC 140
2025
*/

import java.util.Random;

public class Hw3_A 
{
	public static void main(String args[])
	{
      long v,t;
      Random r = new Random();
      
      //increment array size by 100 each time
      for(int i = 0; i<=40; i++)
      {
		   int[] arr = new int[100*i];
         for(int j = 0;j< arr.length; j++)
         {
            arr[i] = r.nextInt(1000);
         }
         
         t = System.nanoTime();
         
         //run each test 50 times and time it
         for(int x = 0; x< 50; x++)                      
            nothing(arr, arr.length);
            
         t = System.nanoTime() - t;
         
         //divide total time by number of runs
         t = t/50;
         System.out.println("Time to do nothing to an array of size: " + arr.length + " was: " + t/1000.0);
      }
	}

   /*Takes in an array and repeatedly swaps
     elements until they are in the original order again
     then repeats log(n) times
   */
	public static void nothing(int[] arr,int n)
	{  
      if (n == 0)
         return;
      
      //repeatedly swap array until it's in the same order it started at
      for(int i = 0; i < arr.length; i++)
         for(int j = i+1; j< arr.length; j++)
         {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
         } 
      //recursive call to function     
      nothing(arr, n/2);
	}
}
