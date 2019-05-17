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
            int q = 1;
            n = 3;
            m = 3;

            long c_lib = 2;
            long c_road = 1;
            int[][] cities = {{1,2},{3,1},{2,3}};
            long result = roadsAndLibraries(n, c_lib, c_road, cities);
            System.out.println(result);
        }
    }
