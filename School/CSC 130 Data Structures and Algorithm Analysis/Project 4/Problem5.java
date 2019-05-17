import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Problem5 {

    private static LinkedList<Integer> adjList[];
    private static boolean[] visited;
    private static int n;
    private static long m;
    private static long numRoads;
    private static int cityCurr;
    
    // Complete the roadsAndLibraries function below.
    static long roadsAndLibraries(int n, long c_lib, long c_road, int[][] cities) {
        long cost = 0;
        numRoads = 0;
        cityCurr = 0;
        if(c_road >= c_lib)
            return n * c_lib;
        adjList = new LinkedList[n]; 
        for(int i = 0; i<n; i++)
        {
            adjList[i] = new LinkedList();
            adjList[i].add(i);
        }
        visited = new boolean[n];
        
        //create an adjecency matrix of the graph
        for(int i = 0; i<cities.length; i++)
        {
            int v1= cities[i][0];
            int v2 = cities[i][1];
            adjList[v1-1].add(v2-1);
            adjList[v2-1].add(v1-1);
        }
        while(cityCurr < n)
        {
            if(!visited[cityCurr])
            {
                numRoads = 0;
                DFS(cityCurr);         
                if(numRoads == 1)
                    cost =  cost + c_lib;
                else
                    cost = cost + (((numRoads - 1) * c_road) + c_lib);
            }
            cityCurr++;
        }
        return cost;
    }

    static void DFS(int i)
    {
         Iterator<Integer> it = adjList[i].listIterator();
        visited[i] = true;
        numRoads++;   
        while(it.hasNext())
        {   
            int j = it.next();
            if(!visited[j])
                DFS(j);
    }
    }  
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] nmC_libC_road = scanner.nextLine().split(" ");

             n = Integer.parseInt(nmC_libC_road[0]);

             m = Long.parseLong(nmC_libC_road[1]);

            long c_lib = Long.parseLong(nmC_libC_road[2]);

            long c_road = Long.parseLong(nmC_libC_road[3]);

            int[][] cities = new int[(int)m][2];

            for (int i = 0; i < m; i++) {
                String[] citiesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int citiesItem = Integer.parseInt(citiesRowItems[j]);
                    cities[i][j] = citiesItem;
                }
            }

            long result = roadsAndLibraries(n, c_lib, c_road, cities);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}