/*Warren Quattrocchi
CSC 140
2025
*/

class Hw3_B
{
   public static void main (String[] args)
   {
      int[][] mat = new int[][] {{1,  2,  3,  4,  5,  6},
                                 {7,  8,  9,  10, 11, 12},
                                 {13, 14, 15, 16, 17, 18},
                                 {19, 20, 21, 22, 23, 24},
                                 {25, 26, 27, 28, 29, 30},
                                 {31, 32, 33, 34, 35, 36}};
      System.out.println(contains2D(mat, 4));
   }
   
   /*helper function, calls contains2DRec
   with required parameters
   */
   public static boolean contains2D(int[][] mat, int k)
   {
      return contains2DRec(mat, k, 0, mat.length-1, 0 , mat[0].length-1);
   }
   
   public static boolean contains2DRec(int[][]mat, int k, int lr, int hr, int lc, int hc)
   {
      //return false if search distance is negative
      if(lr > hr || lc > hc)
         return false;
         
      //find the middle element of the matrix   
      int row = (hr + lr)/2;
      int col = (hc + lc)/2;
      
      //if the key is  in middle index, return true
      if(mat[row][col] == k)
         return true;
      
      //if key is less than middle element
      //search in lower and upper right areas of the matrix
      if(mat[row][col] < k)
         return contains2DRec(mat, k, row + 1, hr, lc, hc)
             || contains2DRec(mat, k, lr, row, col + 1, hc);
      if(mat[row][col] > k)
      
      //if key is greater than middle element
      //search left side and upper right areas
         return contains2DRec(mat, k, lr, hr, lc, col-1)
             || contains2DRec(mat, k, lr, row - 1, col, hc);
      
      return false;
   }

}
/*solution based on image from geekstogeeks
https://www.geeksforgeeks.org/wp-content/uploads/DaCMat3.jpg
*/