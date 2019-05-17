class Main
{
   public static void main(String[] args)
   {
      int[] a = new int[]{5, 4, 1, 9, 0, 10};
      check(a, 0, a.length);
   }
   
   public static void check(int a[], int l, int h)
   {
      int mid = (l+h)/2;
      if(h - l == 0){
         System.out.println(a[mid]);
         return;
      }
      
      check(a, l, mid);
      check(a,mid+1, h);
   }
}