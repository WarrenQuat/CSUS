/*Warren Quattrochci
 * CSC 139
 * Assignment 2 - CPU Scheduling Simulation
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "queue.h"
#include "heap.h"

#define BUFFER_SIZE 1024
#define INPUT_SIZE 20
#define PCB_SIZE 10000
#define SCHED_LENGTH 20

enum type{FCFS, RR, SRTF};
enum state{NEW, WAITING, RUNNING, TERMINATED};

struct Process{
   int arrival_time;
   int burst_time;
   int end_time;
   int pid;
   enum state pState;
   int start_time;
   int waiting_time;
};

void initFlags(struct Process p[], int id[], int numTasks);
void firstComeFirstServe(struct Process p[],int id[], int numTasks);
void roundRobin(struct Process p[],int id[], int numTasks, int quantum);
void shortestRemainingTime(struct Process p[],int id[], int numTasks);
void calculateAverages(struct Process p[], int id[], int tasks, int time, int idle);

int main(int argc, char*argv[]){
   FILE *inputFile;
   int i = 0;
   int id[INPUT_SIZE];
   struct Process processes[PCB_SIZE];
   int quantum;
   char readBuf[BUFFER_SIZE];
   enum type scheduler;
   char sType[SCHED_LENGTH];
   int ta, tb, tp;

   if (argc < 3){
      printf("Usage: scheduler input_file [FCFS|RR|SRTF] [time_quantum]\n");
      exit(0);
   }else{
      if (strcmp(argv[2], "FCFS") == 0){
         scheduler = FCFS;
         strcpy(sType, "FCFS");
      }
      else if (strcmp(argv[2], "RR") == 0){
         scheduler = RR;
         strcpy(sType, "RR");
         quantum = atoi(argv[3]);
      }
      else  if (strcmp(argv[2], "SRTF") == 0){
         strcpy(sType, "SRTF");
         scheduler = SRTF;
      }
      else{
         printf("Usage: scheduler input_file [FCFS|RR|SRTF] [time_quantum]\n");
       exit(0);
      }
   }
   if ((inputFile = fopen(argv[1], "r")) == NULL)
      exit(0);
   while(fgets(readBuf, BUFFER_SIZE - 1, inputFile) != NULL){
      sscanf(readBuf, "%d%d%d", &tp,&ta,&tb);
      id[i] = tp;
      processes[tp].pid  = tp;
      processes[tp].arrival_time = ta;
      processes[tp].burst_time  = tb;
      i++;
   }
   printf("\nScheduling algorithm: %s \n", sType);
   printf("Total %d tasks are read from \"%s\" . press 'enter' to start...", i, argv[1]);
   getchar();
   printf("=================================================================\n"); 

   //Manage scheduling type
   if (scheduler == FCFS)
      firstComeFirstServe(processes,id, i);
   else if(scheduler == RR)
      roundRobin(processes, id, i, quantum);
   else if(scheduler == SRTF)
      shortestRemainingTime(processes,id, i);
   printf("=================================================================\n"); 
}

void firstComeFirstServe(struct Process p[], int id[], int numTasks){
   int completed = 0;
   int curr;
   int i;
   int idle;
   int queued = 0;
   int time;
   createQueue();

   time = 0;
   idle = 0;
   while(queued < numTasks){         //loop until all tasks are queued
      for (i = 0; i<numTasks; i++){  //enqueue all incoming taks in order of arrival time
         int currid = id[i];          
         if(p[currid].arrival_time == time){
            queued++;   
            enqueue(id[i]);
         }
      }
      time++;
   }
   time = 0;
   while(completed < numTasks){   //loop until all tasks are completed
      curr = peek();
      if(p[curr].pState == NEW){  //set starting time on new process and set to running
         p[curr].start_time = time;
         p[curr].pState = RUNNING;
      }
      if(p[curr].burst_time == 0){ //terminate process if completed and dequeue
         completed++;
         p[curr].end_time = time;
         p[curr].pState = TERMINATED;
         dequeue();
      }
      if(p[curr].arrival_time > time){ //check if process is available based on time
         idle++;
         printf("<system time%5d> no process is available\n", time);
         time++;
      }else{  
         if(p[curr].burst_time > 0){  //decrement burst time on running process
            printf("<system time%5d> process%5d is running\n", time,curr);  
            p[curr].burst_time--;
            time++;
         }else{                       //if process is finished
           printf("<system time%5d> process%5d is finished........\n", time, curr);
         }
      }
      for(i = 0; i < numTasks; i ++){ //increment waiting times of waiting processes
         curr = id[i];
         if(p[curr].pState == WAITING)
            p[curr].waiting_time++;
      }
   }
   printf("<system time%5d> All processes finish .....................\n\n");
   calculateAverages(p, id, numTasks, time, idle);
}
//round robin
void roundRobin(struct Process p[], int id[], int numTasks, int quantum){
   int completed = 0;
   int curr;
   int currid;
   int done = 0;
   int i;
   int idle;
   int j;
   int none = 0;
   int time;
   createQueue();
  
   time = 0;
   idle = 0;

   for(i = 0; i < numTasks; i ++){       //check for intial processes arriving at time 0
      curr = id[i];
      if(p[curr].arrival_time == time){
         enqueue(curr);
      }   
   }
   while(completed < numTasks){       //loop until all processes are completed
      done = 0;
      none = 0;
      for(i = 0; i< quantum; i++){    //run same task for the quantum or until process is finished
          if((curr = peek()) != -1){  //if queue is not empty, decrement burst time  
             if(p[curr].pState == NEW){  //check for new process, set starting time
                p[curr].start_time = time;
                p[curr].pState = RUNNING;
             }else{
                p[curr].pState = RUNNING;
             }
             if(p[curr].burst_time == 0){ //break out of quantum if processes finished
                done = 1;
                break;
             }else{
                printf("<system time%5d> process%5d is running\n", time, curr);
                p[curr].burst_time--;
                if(p[curr].burst_time == 0)
                   done = 1;
                time++;
             }
         }else{
             printf("<system time%5d> no process is available\n", time);
             time++;
             idle++;
             none = 1;
             break;
         }         
          for(j = 0; j < numTasks; j++){    //search for new processes and increment waiting time
             currid = id[j];
             if(p[currid].arrival_time == time)
                enqueue(currid);
             if(p[currid].pState == WAITING)
                p[curr].waiting_time++;          
          } 
      }  
      if(none == 0){          //dequeue and enqueue process after quantum, set to waiting
         if(done == 0){
            dequeue();
            enqueue(curr);
            p[curr].pState = WAITING;
         }else{
            printf("<system time%5d> process%5d is finished........\n", time, curr);
            p[curr].pState = TERMINATED;
            dequeue();
            p[curr].end_time = time;
            completed++;
         }
      }else{  //search for new processes arriving at current time
         for(i = 0; i < numTasks; i ++){
            curr = id[i];
            if(p[curr].arrival_time == time)
               enqueue(curr);
         }
      }
   }
   printf("<system time%5d> All processes finish ................\n\n");
   calculateAverages(p, id, numTasks, time, idle);
}  
//shortest remaining time
void shortestRemainingTime(struct Process p[],int id[], int numTasks){
   int completed;
   int curr,currid;
   int done;
   int i;
   int idle;
   int time;
   int timeinc;
   createHeap();  //initialize heap

   time = 0;
   idle = 0;
   timeinc = 1;
   completed = 0;
   while(completed < numTasks){  //loop until all processes are completed
      done = 0;
      if(timeinc == 1)
      for(i = 0; i < numTasks; i++){  //loop through all processes when time is incremented
         currid = id[i];
         if(p[currid].arrival_time == time) //add a process to the heap if it is newly arrived
            insert(p[currid].burst_time, currid);
      } 
      curr = deleteMin(); //remove min burst time
      if(curr != -1){     //set start time if new process
         if(p[curr].pState == NEW){
            p[curr].start_time = time;
            p[curr].pState = RUNNING;
         }else{           //set process to running
            p[curr].pState = RUNNING;
         }                //set to done if burst time is 0
         if(p[curr].burst_time == 0){
            done = 1;
         }else{           //decrement burst time if not done
            printf("<system time%5d> process%5d is running\n", time, curr);
            p[curr].burst_time--;
         }
         if(done == 0){   //increment all processes currently waiting
            for(i = 0; i < numTasks; i++){
               currid = id[i];
               if(p[currid].pState == WAITING)
                  p[currid].waiting_time++;
               } 
            p[curr].pState = WAITING;  //set current proccess to waiting
            insert(p[curr].burst_time, curr);  //add back into heap
            time++;
            timeinc = 1;
         }else{
            printf("<system time%5d> process%5d is finished........\n", time, curr);
            p[curr].end_time = time;
            p[curr].pState = TERMINATED;
            completed++;
            timeinc = 0;
         }
      }else{
         printf("<system time%5d> no process is available\n", time);  
         time++;
         idle++;
         timeinc = 1;
      }          
   } 
   printf("<system time%5d> All processes finish ................\n\n");
   calculateAverages(p, id, numTasks, time, idle);
}   
//initialize all states to waiting and waiting time to 0
void initFlags(struct Process p[], int id[], int numTasks){
   int i;
   for (i = 0; i < numTasks; i++){
      int currid = id[i];
      p[currid].waiting_time = 0;
      p[currid].pState = NEW;
   }
}

//calculate the averages of each algorithm
void calculateAverages(struct Process p[], int id[], int tasks, int  time, int  idle){
   printf("=================================================================\n"); 
   int i;
   double totalR = 0;
   double totalT = 0;
   double totalW = 0;
   double usage;
   for (i = 0; i < tasks; i++){
      int currid = id[i];
      int start = p[currid].start_time;
      int end = p[currid].end_time;
      int response  = start - p[currid].arrival_time;
      int turnaround = end - p[currid].arrival_time;
      totalW = totalW + (p[currid].waiting_time + response);
      totalR = totalR + response;
      totalT = totalT + turnaround;
   }
   //cpu usage
   usage = (((double)time - (double)idle)/(double)time) * 100;
   printf("Average cpu usage      : %.2lf %%\n", usage);

   totalW = (double)(totalW/tasks);
   printf("Average waiting time   : %4.2lf\n", totalW);

   totalR = (double)(totalR/tasks);
   printf("Average response time  : %4.2lf\n", totalR);
  
   totalT = (double)(totalT/tasks);
   printf("Average turnaround time: %4.2lf\n", totalT);
 
}

