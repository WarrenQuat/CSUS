/*Warren Quattrocchi
  CSC 140
  2025
  */
class Hw5{
   public static void main(String[] args)
   {
      int same;
      int total;
      DisjointSet ds;
      //Random r = new Random();
      double p;
      int size = 1000;

      int percent[] = new int[1000];
      int pairst[] = new int[1000];
      int totalt[] = new int[1000];
      double pVal[] = new double[1000];
      int c = 0;
      
      //loop over incremental p values
      for(p = 0; p < .002; p += .00001)
      {
         total = 0;
         same = 0;
         ds = new DisjointSet(size);
         
         //randomly create unions based on p value
         for(int i = 0; i < size - 1; i++)
         {
            for(int j = i + 1; j < size ; j++)
            {
               double rand = (Math.random() * ((1)));
               if(rand < p){
                  ds.union(i,j);
               }
            }
         }
         
         //check for elements in the same component
         //save total and pairs found
         for(int i = 0; i < size-1; i ++)
         {
            for(int j = i + 1; j < size; j ++ )
            {         
               if(ds.sameComponent(i,j))
                  same++;                 
               total++;
            }
         }
         //store all values related to current p value
         percent[c] =  (same * 100)/total;
         pVal[c] = p;
         pairst[c] = same;
         totalt[c] = total;
         c++;
      }
      
      //check for closest p value that results in 50% of pairs
      int min = 0;
      for(int i = 0; i < size; i++)
      {
         if((Math.abs(50 - percent[i])) < (Math.abs(50 - percent[min])))
            min = i;
      }
      String format = String.format("%.5f", pVal[min]);
      System.out.println("p value: " + format);
      System.out.println("pairs: " + pairst[min] + " total: " + totalt[min]);
    }
 }  
   
//disjoint set class   
class DisjointSet
{
   int par[];
   int rank[];
   int size;
   
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