/*Warren Quattrochci
 * CSC 139
 * Assignment 4 - Virtual Memory Scheduling Simulation
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include "queue.h"

#define BUFFER_SIZE 1024
#define INDEX_SIZE 10000

//function headers
void FIFO(int numPages, int numFrames, int numRequests, int requests[]);
void LRU(int numPages, int numFrames, int numRequests, int requests[]);
void OPT(int numPages, int numFrames, int numRequests, int requests[]);
void initCounter(int counters[], int frames[], int numFrames);
int searchFrames(int frames[], int page, int numFrames);
int findMin(int frames[], int counters[], int numFrames);
int findLongest(int frames[], int requests[], int numFrames, int numRequests, int i);

//get all file inputs and call designated algorithm
int main(int argc, char*argv[]){
   FILE *inputFile;
   int i = 0;
   char sType[20];
   char readBuf[BUFFER_SIZE];
   int numPages;
   int numFrames;
   int numRequests;
   int tempReq;

   if (argc < 3){
      printf("Usage: scheduler input_file [FIFO|LRU|OPT]\n");
      exit(0);
   }else{
      if (strcmp(argv[2], "FIFO") == 0){
         strcpy(sType, "FIFO");
      }
      else if (strcmp(argv[2], "LRU") == 0){
         strcpy(sType, "LRU");
      }
      else  if (strcmp(argv[2], "OPT") == 0){
         strcpy(sType, "OPT");
      }
      else{
         printf("Usage: scheduler input_file [FIFO|LRU|OPT]\n");
         exit(0);
      }
   }
   //read file
   if ((inputFile = fopen(argv[1], "r")) == NULL)
      exit(0);
   fgets(readBuf, BUFFER_SIZE - 1, inputFile);
   sscanf(readBuf, "%d%d%d", &numPages, &numFrames, &numRequests);
   int requests[numRequests - 1];

   while(fgets(readBuf, BUFFER_SIZE - 1, inputFile) != NULL){
      sscanf(readBuf, "%d", &tempReq);
      requests[i] = tempReq;
      i++;
   }
   printf("\n");
   if (strcmp(sType, "FIFO") == 0)
      FIFO(numPages, numFrames, numRequests, requests);
   if(strcmp(sType, "LRU") == 0)
      LRU(numPages, numFrames, numRequests, requests);
   if(strcmp(sType, "OPT") == 0)
      OPT(numPages,numFrames, numRequests, requests);
}

/*FIFO page replacement algorithm
 * uses a queue data structure
 * all output is done within the queue
 */
void FIFO(int numPages, int numFrames, int numRequests, int requests[])
{
   initQueue(numFrames);
   int i;
   int pageFaults = 0;
   int frame;
   for(i = 0; i < numRequests; i ++){
      if((frame = searchItem(requests[i])) == -1){
         insert_item(requests[i]);
         pageFaults ++;
      }
   }
   printf("%d Page faults\n\n", pageFaults);
}

/*Least recently used paging algorithm
 * Chooses which page to replace based on backlog of requests
 */
void LRU(int numPages, int numFrames, int numRequests, int requests[])
{
   int counters[INDEX_SIZE];
   int frames[numFrames];
   initCounter(counters,frames,numFrames);
   int pageFaults = 0;
   int fi = 0;
   int i;
   int pageI;   

   //loop through all frames
   for(i = 0; i < numRequests; i++){
      //if page is not found in frames currently
      if((pageI = searchFrames(frames, requests[i], numFrames)) == -1){
         //add it to the frames if they are currently not filled
         if(fi < numFrames){
            frames[fi] = requests[i];
            pageFaults++;
            counters[frames[fi]] = i;
            printf("Page %d loaded into frame %d \n", requests[i], fi);
            fi++;
         //find least recently used page and replace
         }else{
            int min  = findMin(frames, counters,numFrames);
            int old = frames[min];
            frames[min] = requests[i];
            counters[frames[min]] = i;
            printf("Page %d unloaded from frame %d, ",old, min);
            printf("Page %d loaded into frame %d \n", requests[i], min);
            pageFaults++; 
         }
      //else update counter
      }else{
         counters[frames[pageI]] = i;
         printf("Page %d already in frame %d \n", requests[i], pageI);
     }
   }
   printf("%d Page faults \n\n", pageFaults);
}

/*Optimal page replacement algorithm
 * chooses page to replace by looking ahead in requests
 */
void OPT(int numPages, int numFrames, int numRequests, int requests[]){
   int counters[INDEX_SIZE];
   int frames[numFrames];
   initCounter(counters,frames,numFrames);
   int pageFaults = 0;
   int i, pageI;
   int fi = 0;

   //loop through all requests
   for(i = 0; i < numRequests; i++){
     //if page is not found in frames currently
      if((pageI = searchFrames(frames,requests[i],numFrames)) == -1){
         //add it if the list has not been initially filled yet
         if(fi < numFrames){
            printf("Page %d loaded into frame %d \n", requests[i], fi);
            frames[fi] = requests[i];
            pageFaults++;
            fi++;
         //else find the page that is not requested soon and replace
         }else{
            int max = findLongest(frames, requests, numFrames, numRequests, i);
            int old = frames[max];
            frames[max] = requests[i];
            pageFaults++;
            printf("Page %d unloaded from frame %d, ",old, max);
            printf("Page %d loaded into frame %d \n", requests[i], max);
         }
      //else page is already in frames
      }else{
         printf("Page %d already in frame %d \n", requests[i], pageI);
      }            
   }
   //print page faults
   printf("%d Page faults \n\n", pageFaults);
}

//initialize counters of pages to max size for LRU
//and frames to -1 to represent unfilled
void initCounter(int counters[], int frames[], int numFrames){
   int i;
   for(i = 0; i < INDEX_SIZE; i ++)
      counters[i] = INT_MAX;
   for(i = 0; i < numFrames; i ++)
      frames[i] = -1;
}

//search through frames to check if a page is present
//used by LRU and OPT
int searchFrames(int frames[], int page, int numFrames){
   int i;
   int index = -1;
   for(i = 0; i < numFrames; i ++){
      if(frames[i] == page){
         index = i;
      }
   }
   return index;
}

//find the page least recently used
//used for LRU
int findMin(int frames[], int counters[], int numFrames){
   int i;
   int min = 0;
   for(i = 0; i < numFrames; i ++){
      if(counters[frames[i]] < counters[frames[min]]){
         min = i;
        // printf("minimum page %d in frame %d\n", frames[min], min);
      }
   }
   return min;
}

//find the page that isn't requested for the longest time
//used for OPT
int findLongest(int frames[], int requests[], int numFrames, int numRequests, int s){
   int i, j;
   int longest;
   int start;
   longest = 0;
   int maxI;
   //loop through all frames
   for(i = 0; i < numFrames; i ++){
      j = 0;
      start = s;

      //loop through requests with current frame
      //until the request equals the frame
      //break if frame in never found
      while(frames[i] != requests[start]){
          if(start == numRequests){
             j = INT_MAX;
             break;
          }
          start++;
          j++;
      }
      //swap with new index if time is longer
      if (j > longest){
         longest = j;
         maxI = i;
      }
   }
   return maxI;
}
