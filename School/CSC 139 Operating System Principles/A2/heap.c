/*Warren Quattrocchi
 * CSC 139
 * Assingment 2
 */

#include <stdio.h>
#include <limits.h>
#include "heap.h"

#define HEAP_SIZE 21


int heapb[HEAP_SIZE];
int heapp[HEAP_SIZE];
int size;

void createHeap(){
   size = 0;
   heapb[0] = -INT_MAX;
}
//insert method
void insert(int b, int p){
   size++;
   heapb[size] = b;
   heapp[size] = p;
   int curr = size;
   while(heapb[curr/2] >= b){
      heapb[curr] = heapb[curr/2];
      heapp[curr] = heapp[curr/2];
      curr = curr/2;
   }
   heapb[curr] = b;
   heapp[curr] = p;
}
//delete min
int deleteMin(){
   if(size == 0)
      return -1;
   int minElement, child, curr;
   int lastElementb,lastElementp;
   minElement = heapp[1];
   lastElementb = heapb[size];
   lastElementp = heapp[size];
   size--;
   for(curr = 1; curr*2 <= size; curr = child){
      child = curr*2;
      if(child != size && heapb[child + 1] <= heapb[child])
         child++;

      if(lastElementb  >= heapb[child]){
         heapb[curr] = heapb[child];
         heapp[curr] = heapp[child];
      }else{
         break;
      }
   }
      heapb[curr] = lastElementb;
      heapp[curr] = lastElementp;

      return minElement;
}

int peekHeap(){
   return heapp[1];
}
