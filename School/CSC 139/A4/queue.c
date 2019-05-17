/*Warren Quattrocchi
 * CSC 139 - Section 7
 * Assignment 4
 * queue.c
 */
#include <stdio.h>
#include <stdlib.h>
#include "queue.h"

int *queue;
int in;            //current index to be added
int frames;

void initQueue(int size){ //initialize all buffer values to 0
   in = 0;
   frames = size;
   queue = (int * )malloc(sizeof(int) * size);
   int i;
   for(i = 0; i < size; i++)
      queue[i] = -1;
}

int insert_item(int item){
   if(queue[in] != -1){
      int old = replace_item(item);
      return old;
   }
   else{
      printf("Page %d loaded into frame %d \n", item, in); 
      queue[in] = item;           //else set in to item
      in = (in + 1) % frames; //increment in and counter
      return 0;
   }
}

int replace_item(int otherPage){
   printf("Page %d unloaded from frame %d, ", queue[in], in);
   printf("Page %d loaded into frame %d \n", otherPage, in); 
   queue[in] = otherPage;
   in = (in + 1) % frames;
   return 0;
}
int searchItem(int page)
{
   int found = -1;
   int i = 0;
   for(i = 0; i < frames; i ++)
      if(queue[i] == page)
      {
         found = i;
         printf("Page %d already in frame %d \n", page, i);
      }
   return found;
}
int currentFrame()
{
   return in;
}
