/*Warren Quattrocchi
 * CSC 139
 * Assignment 2
 */

/*Very simple circular queue array implementation
 * assumes max size is 20 and will never be full
 */
#include <stdio.h>
#include <stdlib.h>
#include "queue.h"

#define SIZE 20

int size, front, back;
int arr[SIZE];

void createQueue(){
   front = -1;
   back = -1;
}

void enqueue(int data){
   if (front == -1){
      front = 0;
      back = 0;
   }else{
      if(back == size-1)
         back = 0;
      else
         back = back + 1;
   }
   arr[back] = data;
}

int peek(){
   if(front == -1 && back == -1)
      return -1;
   return arr[front];
}

int dequeue(){
   if(front == -1 && back == -1)
      return 0;
   int data = arr[front];
   if(front == back){
      front = -1;
      back = -1;
   }else{
      if(front == size -1)
         front = 0;
      else
         front = front + 1;
   }
   return data;
}

      
