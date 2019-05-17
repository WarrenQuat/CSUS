/*Warren Quattrocchi
 * CSC 139 - Section 7
 * Assignment 3: Process Synchronization
 * proj3.c
 */

#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <pthread.h>
#include <semaphore.h>

#include "buffer.h"

#define MAX_SLEEP 5

int seed;               //seed value to be shared by threads
pthread_mutex_t mutex;  //mutex lock
sem_t empty, full;      //full and empty semaphores

//fuction prototypes for thread entries
void *producer(void *param);
void *consumer(void *param);

int main(int argc, char * argv[]){
   int consumers, producers;
   int i;
   int sleepTime;
   seed = time(NULL); //initialize random seed by time
   //create thread id and attr variables
   pthread_t tid;
   pthread_attr_t attr;

   //initalize mutex and semaphores
   pthread_mutex_init(&mutex, NULL);
   sem_init(&full, 0 , 0);
   sem_init(&empty, 0, BUFFER_SIZE);


   if(argc < 4){  //exit if argc < 4
      printf("usage: proj3 [sleep time] [producers] [consumers]\n");
      exit(0);
   }else{
      sleepTime = atoi(argv[1]);
      producers = atoi(argv[2]);
      consumers = atoi(argv[3]);
   }
   pthread_attr_init(&attr); //initalize threads
   initBuffer();
   
   for(i = 0; i < consumers; i++){ //create consumer threads
      pthread_create(&tid,&attr,consumer,NULL);
   }
   for(i = 0; i < producers; i++){ //create producer threads
      pthread_create(&tid,&attr,producer,NULL);
   }
   sleep(sleepTime); //sleep for user defined time

}
/*producer function
 *increments random seed to avoid all threads having same sleep time
 *generates a random item
 *sleeps for random time and then tries to add item to buffer
 */
void *producer(void *param){
   buffer_item item;
   seed++;
   while(1){
      //sleep for a random period of time
      sleep(rand_r(&seed)% MAX_SLEEP);
      item = (rand_r(&seed));
      sem_wait(&empty);            //must wait until buffer has room
      pthread_mutex_lock(&mutex);  //must wait if another processor is in critical section
      if(insert_item(item)) 
         printf("Buffer overflow\n");
      else
         printf("Producer produced %d\n", item);
      sem_post(&full);             //signal semaphore to add 1 to full
      pthread_mutex_unlock(&mutex);//unlock the critical section (buffer)
   
   }
}
/*Consumer function
 *increments random seed to avoid all threads having same sleep time
 *sleeps for a random amount of time
 *then attempts to remove item from the buffer
 */
void *consumer(void *param){
   buffer_item item;
   seed++;
   while(1){
      sleep(rand_r(&seed) % MAX_SLEEP);
      sem_wait(&full);                //waits for an item to be available
      pthread_mutex_lock(&mutex);     //waits for critical section be free, then locks
      if(remove_item(&item))
         printf("Buffer underflow\n");
      else
         printf("Consumer consumed %d\n",item);
      sem_post(&empty);              //signal semaphore to decrement 1 from empty
      pthread_mutex_unlock(&mutex);  //unlock the critical section (buffer)
   }
}
