/*Warren Quattrocchi
 * CSC 140
 * 2025
 * TSP approx
 */

import java.util.ArrayList;

public class TSP 
{
	public static void main(String[] args)
	{
		int size = Integer.parseInt(args[0]);
		Graph graph = new Graph(size);
			
		//generate a graph based on input
		for(int i = 1; i < args.length - 1; i = i + 3)
		{
	       int a = Integer.parseInt(args[i]);
	       int b = Integer.parseInt(args[i+1]);
	       int weight = Integer.parseInt(args[i+2]);
	       graph.addEdge(a, b, weight);
		}
		//approximate the TSP of created graph
		tspApprox(graph,0);
	}
	
	//tsp approx method
	public static void tspApprox(Graph g, int start)
	{
		//generate an mst of graph
		Graph mst = g.kruskals();
		//generate list of aLL visited vertices
		ArrayList<Integer> mst2 = mst.dfs(start);
		boolean seen[] = new boolean[mst.getSize()];
		for(int i = 0; i < mst2.size(); i ++)
		{
			int curr = mst2.get(i);		
			//print out vertex if it hasn't yet been seen
			if(!seen[curr])
			{
				System.out.print(curr + " " );
				seen[curr] = true;
			}
		}
      System.out.println();
	}
}

class Graph {

	private ArrayList<GraphNode>[] graph;
	private int size;
	
	//initialize adjacency list, heap, and size
	@SuppressWarnings("unchecked")
	public Graph(int size)
	{
		this.size = size;
		graph = new ArrayList[size];
		
		for(int i = 0; i < size; i ++)
			graph[i] = new ArrayList<GraphNode>();
	}
	
	//add an undirected edge to the graph
	public void addEdge(int src, int dst, int weight)
	{
		graph[src].add(new GraphNode(src,dst,weight));
		graph[dst].add(new GraphNode(dst,src,weight));
	}
	
	//Generate a MST from the current graph
	//and return it
	public Graph kruskals()
	{
		sortEdges();
		DisjointSet s = new DisjointSet(this.getSize());
		Graph mst = new Graph(this.getSize());
		GraphNode min;
		Heap heap = new Heap();
		
		//replace this with a heap
		//getmin edge
		for(int i = 0; i < this.getSize(); i++)
			for(int j = 0; j < graph[i].size(); j ++)
				heap.insert(graph[i].get(j));
		min = heap.extractMin();
		while(min != null)
		{
			int x = s.find(min.getSrc());
			int y = s.find(min.getDst());
			if(x != y)
			{
				s.union(x,y);
				//System.out.println("edge from " + min.getSrc() + " to " + min.getDst() + " added");
				mst.addEdge(min.getSrc(), min.getDst(), min.getWeight());
			}
			min = heap.extractMin();		
		}	
		//return the generated mst as a new graph
		return mst;
	}
	
	//dfsRecur calling method
	public ArrayList<Integer> dfs(int start)
	{
		//create list for all vertices that are visited during the dfs
        sortEdges();
		ArrayList<Integer> nodes = new ArrayList<Integer>();
		boolean visited[] = new boolean[size];
		dfsRecur(start, visited,nodes);
		//return list of all visited vertices
		return nodes;
	}

	//dfs recursive function, visit all vertices from starting point
	private void dfsRecur(int v, boolean[] visited, ArrayList<Integer> nodes)
	{
		visited[v] = true;
		nodes.add(v);
		for(int i = 0; i < graph[v].size(); i ++)
		{
			int curr = graph[v].get(i).getDst();
			if(!visited[curr])
				dfsRecur(curr, visited, nodes);
		}
		nodes.add(v);
	}

	public int getSize() 
	{
		return size;
	}
	
	//sort edges by destination so DFS will traverse similarly to adjacency matrix
	//O(v * e^2) selection sort
	public void sortEdges()
	{
	  int length = getSize();
      for(int i = 0; i < length; i++)
      {
         int n = graph[i].size();
         for(int j = 0; j < n - 1; j++)
         {
            int min = j;
            for(int k = j + 1; k < n; k++)
               if(graph[i].get(k).getDst() < graph[i].get(min).getDst())
                  min = k;
            
            GraphNode temp = graph[i].get(min); 
            graph[i].set(min,  graph[i].get(j)); 
            graph[i].set(j,  temp); 
         }
      }
	}
}
class GraphNode implements Comparable<GraphNode> {

	private int cost;
	private int src;
	private int dst;
	private boolean visited;
	
	//create a new edge with src, dst, and weight
	public GraphNode(int src, int dst, int weight)
	{
		this.cost = weight;
		this.dst = dst;
		this.src = src;
		this.visited = false;
	}
	//create a new edge with only a weight
	//used for initial minimum edge
	public GraphNode(int weight)
	{
		this.cost = weight;
	}
	
	public int getWeight()
	{
		return cost;
	}
	public void setMarked()
	{
		this.visited = true;
	}
	public boolean getMarked()
	{
		return visited;
	}
	public int getSrc()
	{
		return src;
	}
	
	public int getDst()
	{
		return dst;
	}

	@Override
	public int compareTo(GraphNode o) {
		if (this.getWeight() == o.getWeight())
			return 0;
		else if(this.getWeight() > o.getWeight())
			return 1;
		else
			return -1;
				
	}
}

//disjoint set used to generate kruskals mst
class DisjointSet
{
   private int par[];
   private int rank[];
   private int size;
   
   public DisjointSet(int s)
   {
      par = new int[s];
      rank = new int[s];
      this.size = s;
      makeSets();
   }
   
   //find with path compression
   int find(int x)
   {
      if(par[x] != x)
         par[x] = find(par[x]);
      return par[x];
   }
   
   //union x and y
   void union(int x, int y)
   {
      x = find(x);
      y = find(y);
      
      if(x==y)
         return;
      
      if(rank[x] < rank[y])
         par[x] = y;
      else if (rank[y] < rank[x])
         par[y] = x;
      else{
         par[x] = y;
         rank[y] = rank[y]+1;
      }
   }
   
   //make sets
   void makeSets()
   {
      for(int i = 0; i <size; i ++)
         par[i] = i;
   }
   
   //check if elements in same component
   boolean sameComponent(int u,int v)
   {
      if (find(u) == find(v))
         return true;
      else
         return false;
   }
}

//heap class for returning minimum edge 
//uses arraylist 
//based on heap algorithms from textbook converted to minheap
class Heap
{
	private ArrayList<GraphNode> h;
	public Heap()
	{
		h = new ArrayList<GraphNode>();
		h.add(null);
	}
	
	//get parent
	public int parent(int s)
	{
		return s/2;
	}
	
	//get left child
	public int left(int s)
	{
		return 2*s;
	}
	
	//get right child
	public int right(int s)
	{
		return (2*s) + 1;
	}
	
	public void insert(GraphNode g)
	{
		h.add(g);
		int curr = h.size() - 1;
		while(curr > 1 && h.get(curr).compareTo(h.get(parent(curr))) < 0)
		{
			GraphNode temp = h.get(curr);
			h.set(curr,h.get(parent(curr)));
			h.set(parent(curr), temp);
			curr = parent(curr);
		}
	}
	
	public GraphNode extractMin()
	{
		if(h.size() == 1)
			return null;
		GraphNode min = h.get(1);
		h.set(1, h.get(h.size() -1));
		h.remove(h.size() - 1);
		minHeapify(1);
		return min;
	}
	
	public void minHeapify(int i)
	{
		int l = left(i);
		int r = right(i);
		int smallest;
		if(l < h.size() && h.get(l).compareTo(h.get(i)) < 0)
			smallest = l;
		else
			smallest = i;
		if(r < h.size() && h.get(r).compareTo(h.get(smallest)) < 0)
			smallest = r;
		if (smallest != i)
		{
			GraphNode temp = h.get(i);
			h.set(i, h.get(smallest));
			h.set(smallest, temp);
			minHeapify(smallest);
		}
	}
}
