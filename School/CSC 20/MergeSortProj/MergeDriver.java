/*Merge Sort
Warren Quattrocchi
CSC 20

*/

public class MergeDriver
{
   public static void main(String[]  args)
   {
      String[] letters = {"Apple", "Zebra", "balloon", "Moon", "Quiet", "zipper", "Moose","Xylophone","extra", "Ant", "Water"};
      System.out.println("Before Sort");
      for(int i = 0; i < letters.length; i++)
      {
         System.out.print(letters[i]+ " ");
      }
      
      mergeSort(letters, 0, letters.length-1);
      
      System.out.println("\nAfter Sort");
      for(int i = 0; i < letters.length; i++)
      {
         System.out.print(letters[i] + " ");
      }
   }
   
   public static void mergeSort(String[] letters, int low, int high)
   {
      if(low == high)
         return;
      int mid =  (low + high)/2;
      mergeSort(letters,low, mid);
      mergeSort(letters,mid+1, high);
      merge(letters, low, mid, high);
   }
   
   public static void merge(String[] letters, int low,int mid,int high)
   {
      int n1 = mid- low + 1;
      int n2 = high - mid;
      String[] tempL = new String[n1];
      String[] tempR = new String[n2];
      
      for(int i = 0; i<n1; i++)
      {
         tempL[i] = letters[low+i];
      }
      for(int j = 0; j < n2; j++)
      {
         tempR[j] = letters[mid + 1 + j];
      }
      int i = 0;
      int j = 0;
      int k = low;
      while(i < n1 && j < n2)
      {
         if(tempL[i].toLowerCase().compareTo(tempR[j].toLowerCase()) <= 0)
         {
            letters[k] = tempL[i];
            i++;
         }else{
            letters[k] = tempR[j];
            j++;
         }
       k++;
      }
      
      while(i<n1)
      {
         letters[k] = tempL[i];
         i++;
         k++;
      }
      while(j< n2)
      {
         letters[k] = tempR[j];
         j++;
         k++;
      }    
   }   
     
}