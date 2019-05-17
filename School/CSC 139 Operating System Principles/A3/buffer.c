/*Warren Quattrocchi
 * CSC 139 - Section 7
 * Assignment 3: Process Synchronization
 * buffer.c
 */

#include <pthread.h>
#include <stdio.h>
#include "buffer.h"

buffer_item buffer[BUFFER_SIZE];
int in;            //current index to be added
int out;           //current item to be removed
int counter;       //# items in buffer

void initBuffer(){ //initialize all buffer values to 0
   in = 0;
   out = 0;
   counter = 0;
}

int insert_item(buffer_item item){ 
   if(counter == BUFFER_SIZE)   //if counter == size of buffer
      return -1;                   //return -1
   buffer[in] = item;           //else set in to item
   in = (in + 1) % BUFFER_SIZE; //increment in and counter
   counter++;
   return 0;
}

int remove_item(buffer_item *item){
   if(counter == 0)           //if counter == 0
      return -1;                //return -1
   buffer_item nextConsumed = buffer[out];
   out = (out + 1) % BUFFER_SIZE;
   *item = nextConsumed;     //else return out and increment by 1
   counter--;
   return 0;
}
