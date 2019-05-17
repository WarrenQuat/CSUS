import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Problem2 {

    // Complete the insertionSort1 function below.
    static void insertionSort1(int n, int[] arr) {
        for (int i = 1; i< n; ++i)
        {
            int min = arr[i];
            int j = i-1;          
            
            while(j>= 0 && arr[j] > min)
            {              
                arr[j+1] = arr[j];
                for(int k = 0; k<n; k++)
                    System.out.print(arr[k] + " ");
                System.out.println("");
                j--;         
            }      
            arr[j+1] = min;
        }
        for(int k = 0; k<n; k++)
          System.out.print(arr[k] + " ");
    }

    public static void main(String[] args) {
        int n = 5;
        int[] arr = {2,4,6,8,3};
        insertionSort1(n, arr);

    }
}