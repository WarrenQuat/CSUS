/*Warren Quattrocchi
 * 2025
 * CSC 140
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void pmerge(int t[], int p1, int r1, int p2, int r2, int a[], int p3);
int binarySearch(int x, int t[], int p, int r);
void swap(int * x, int * y);
void ms(int a[], int b[], int p, int r, int a_to_b);
void mergesort140(int src[], int dst[], int n);

void ms_serial(int a[], int b[], int p, int r, int a_to_b);
void pmerge_serial(int t[], int p1, int r1, int p2, int r2, int a[], int p3);

void pmerge(int t[], int p1, int r1, int p2, int r2, int a[], int p3)
{
   int n1 = r1 - p1 + 1;
   int n2 = r2 - p2 + 1;

   if (n1 < n2)
   {
      swap(&p1, &p2);
      swap(&r1, &r2);
      swap(&n1, &n2);
   }
   if (n1 == 0)
      return;
   else
   {
      int q1 = ((p1 + r1)/2);
      int q2 = binarySearch(t[q1], t, p2, r2);
      int q3 = p3 + (q1 - p1) + (q2 - p2);
      a[q3] = t[q1];
         #pragma omp task
         pmerge(t, p1, q1 - 1, p2, q2 - 1, a, p3);
      
         pmerge(t, q1 + 1, r1, q2, r2, a, q3 + 1);
      #pragma omp taskwait
   }
}

int  binarySearch(int x, int t[], int p, int r)
{
   int high;
   int low = p;
   if (p > r+1)
      high = p;
   else
      high = r+1;

   while (low < high)
   {
      int mid = ((low + high)/2);
      if (x <= t[mid])
         high = mid;
      else
         low = mid+1;
   }
   return high;
      
}

void swap(int * x, int * y)
{
   int temp = *x;
   *x = *y;
   *y = temp;
}

void ms(int a[], int b[], int p, int r, int a_to_b)
{
   if(p < r)
   {
      /*execture serial merge sort when size is small
        to avoid large overhead
      */
      if(r-p < 1024)
      {
         ms_serial(a, b, p, r, a_to_b);
      }
      else
      {
         int q = (p + r)/2;
            #pragma omp task
            ms(a, b, p, q, !a_to_b);
         ms(a, b, q+1, r, !a_to_b);
      #pragma omp taskwait
      if(a_to_b)
         pmerge(a, p, q, q+1, r, b, p);
      else
         pmerge(b,p,q,q+1,r,a,p);
      } 
   } 
}

void mergesort140(int src[], int dst[], int n)
{
   #pragma omp parallel
   {
      memcpy(dst,src,n*sizeof(int));
         #pragma omp single
         ms(src,dst,0,n-1,1);
   }
}

void pmerge_serial(int t[], int p1, int r1, int p2, int r2, int a[], int p3)
{
   int n1 = r1 - p1 + 1;
   int n2 = r2 - p2 + 1;

   if (n1 < n2)
   {
      swap(&p1, &p2);
      swap(&r1, &r2);
      swap(&n1, &n2);
   }
   if (n1 == 0)
      return;
   else
   {
      int q1 = ((p1 + r1)/2);
      int q2 = binarySearch(t[q1], t, p2, r2);
      int q3 = p3 + (q1 - p1) + (q2 - p2);
      a[q3] = t[q1];
      pmerge_serial(t, p1, q1 - 1, p2, q2 - 1, a, p3);
      pmerge_serial(t, q1 + 1, r1, q2, r2, a, q3 + 1);
   }
}

void ms_serial(int a[], int b[], int p, int r, int a_to_b)
{
   if(p < r)
   {
     int q = (p + r)/2;
      ms_serial(a, b, p, q, !a_to_b);
      ms_serial(a, b, q+1, r, !a_to_b);
      if(a_to_b)
         pmerge_serial(a, p, q, q+1, r, b, p);
      else
         pmerge_serial(b,p,q,q+1,r,a,p);
   }  
}
