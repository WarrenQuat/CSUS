public class What
{

public static void main(String[] args)
{
  int[] arr = {10,3,14,1,22,-8,7,6,17,3};
  insertionSort(arr);
   for(int i = 0; i< arr.length; i++)
   {
     System.out.print(arr[i] + " ");
  } 
   
   int[] arr2 = {21,17,14,1,22,6,-4,6,17,3};
   selectionSort(arr2);
   for(int i = 0; i< arr2.length; i++)
   {
      System.out.print(arr2[i] + " ");
   } 
}

public static void selectionSort(int[] arr)
{
   int n = arr.length;
   for(int i = 0; i< n-1; i++)
   {
      int min = i;
      for(int j = i+ 1; j< n; j++)
         if(arr[j] < arr[min])
            min = j;
    
    int temp =  arr[i];
    arr[i] = arr[min];
    arr[min] = temp;
   }
}

public static void insertionSort(int[] arr)
{
   int n = arr.length;
   for(int i = 1; i< n; i++)
   {
      int key = arr[i];
      int j = i-1;
      while(j>= 0 && arr[j] > key)
      {
         arr[j+1] = arr[j];
         j--;
      }
     arr[j+1] = key;
   }
}
}