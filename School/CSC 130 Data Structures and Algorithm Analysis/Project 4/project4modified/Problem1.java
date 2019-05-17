import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Problem1 {

    // Complete the introTutorial function below.
    static int introTutorial(int V, int[] arr) {
        for(int i = 0; i<arr.length; i++)
        {
            if(arr[i] == V)
                return i;
        }
    return -1;
    }

    public static void main(String[] args) throws IOException {

        int V = 4;

        int n = 6;

        int[] arr = {1,4,5,7,9,12};

        int result = introTutorial(V, arr);

       System.out.println(result);
    }
}