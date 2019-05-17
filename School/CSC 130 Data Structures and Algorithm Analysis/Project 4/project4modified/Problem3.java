import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Problem3 {

    // Complete the quickSort function below.
    static int[] quickSort(int[] arr) {
        int low = 0;
        int high = arr.length- 1;
        
        int pivot = arr[low];
      while(low < high)
      {   
        while(arr[low] < pivot)
         low++;
        while(arr[high] > pivot)
         high--;
         
         int temp = arr[low];
         arr[low] = arr[high];
         arr[high] = temp;
       }
   return arr;
 }
 
    public static void main(String[] args) throws IOException {

        int n = 5;

        int[] arr = {4,5,3,7,2};

        int[] result = quickSort(arr);

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);

            if (i != result.length - 1) {
                System.out.print(" ");
            }
        }
    }
}