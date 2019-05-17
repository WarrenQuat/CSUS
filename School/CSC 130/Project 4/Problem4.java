import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Problem4 {

    // Complete the bfs function below.
    static int[] bfs(int n, int m, int[][] edges, int s) {
    boolean[][] adjMat = new boolean[n][n];
    for(int i = 0; i<edges.length; i++)
    {
       int v1 = edges[i][0];
       int v2 = edges[i][1];
       adjMat[v1 - 1][v2 - 1]= true;
       adjMat[v2 - 1][v1 - 1]= true;
    }

    int size = adjMat.length;
    Queue<Integer> queue = new LinkedList<>();
    int[] arr = new int[size];
        for(int i = 0; i<size; i++)
            arr[i] = -1;
    boolean[] visited = new boolean[size];
    visited[s - 1] = true;
    queue.add(s - 1);
        
    while(!queue.isEmpty())
    {
        int curr = queue.remove();
        for(int i = 0; i<size; i++)
        {
            if(adjMat[curr][i] && !visited[i])
            {
                queue.add(i);
                visited[i] = true;
                if(arr[curr] == -1)
                    arr[curr] = 0;
                arr[i] = arr[curr] + 6;
            }
        }
    }    
    for(int i = 0; i<size; i++)
    {
        if(i != s - 1)
              System.out.print(arr[i] + " ");
    }
    System.out.println();
    return arr;
}

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] nm = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nm[0]);

            int m = Integer.parseInt(nm[1]);

            int[][] edges = new int[m][2];

            for (int i = 0; i < m; i++) {
                String[] edgesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int edgesItem = Integer.parseInt(edgesRowItems[j]);
                    edges[i][j] = edgesItem;
                }
            }

            int s = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] result = bfs(n, m, edges, s);

            for (int i = 0; i < result.length; i++) {
                if(i != s- 1)
                {
                    bufferedWriter.write(String.valueOf(result[i]));

                     if (i != result.length - 1) {
                         bufferedWriter.write(" ");
                }
                }
            }

            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}