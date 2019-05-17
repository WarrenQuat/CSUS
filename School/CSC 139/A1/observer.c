/*Warren Quattrocchi
 * CSC 139
 * Assignment 1
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/time.h>
#define LB_SIZE 1024

enum type{STANDARD,SHORT,LONG};

void stdReport   (FILE *rptFile, char lineBuf[]);
void shortReport (FILE *rptFile, char lineBuf[]);
void longReport  (FILE *rptFile, char lineBuf[], int interval, int duration);
void secondsFormat(int time);
void sampleLoadAvg(FILE *rptFile, char lineBuf[]);

int main(int argc, char *argv[])
{
    //initialize
    FILE *thisProcFile;
    char repTypeName[16];
    int interval, duration;
    char c1, c2;
    struct timeval now;
    char lineBuf[LB_SIZE];
    enum type reportType;
    
    reportType = STANDARD;
    strcpy(repTypeName, "Standard");   
    if(argc > 1)
    {
        sscanf(argv[1], "%c%c", &c1, &c2);
        if(c1 != '-')
        {
            fprintf(stderr, "usage: observer [-s] [-l int dur]\n");
            exit(1);
        }
        if(c2 == 's')
        {
            reportType = SHORT;
            strcpy(repTypeName, "Short");
        }
        if(c2 == 'l')
        {
            reportType = LONG;
            strcpy(repTypeName, "Long");
            interval = atoi(argv[2]);
            duration = atoi(argv[3]);
        }
    }
    //get time of day, report type, and host name
    gettimeofday(&now, NULL);
    printf("\nStatus report type %s at %s", repTypeName, ctime(&(now.tv_sec)));
    thisProcFile = fopen("/proc/sys/kernel/hostname", "r");
    fgets(lineBuf, LB_SIZE+1, thisProcFile);
    printf("Machine hostname: %s", lineBuf);
    fclose(thisProcFile);

    if(strcmp(repTypeName, "Standard") == 0)
    {
        printf("-------------------------------\n\n");
        stdReport(thisProcFile, lineBuf);
    }
    if(strcmp(repTypeName, "Short") == 0)
    {
        printf("-------------------------------\n\n");
        shortReport(thisProcFile, lineBuf);
    } 
    if(strcmp(repTypeName, "Long") == 0)
    {
        printf("-------------------------------\n\n");
        longReport(thisProcFile,lineBuf, interval,duration);
    }
}

//generate a standard report
void stdReport(FILE *rptFile, char lineBuf[])
{
    int time;
    printf("CPU info: \n");
    rptFile = fopen("/proc/cpuinfo", "r");
    while(fgets(lineBuf, LB_SIZE+1, rptFile) != NULL)
    {
        if(strncmp(lineBuf, "model name", 10) == 0)
        {
            printf("%s\n", lineBuf);
            break;
        }
    }
    fclose(rptFile); 
    printf("Kernel version: \n");
    rptFile = fopen("/proc/version", "r");
    fgets(lineBuf, 38, rptFile);
    printf("%s \n\n", lineBuf);
    fclose (rptFile);
    printf("System uptime: \n");
    rptFile = fopen("/proc/uptime", "r");
    fscanf(rptFile,"%d", &time);
    secondsFormat(time);
    fclose(rptFile);
}

//generate a short report
void shortReport(FILE *rptFile, char lineBuf[])
{
    int userTime, sysTime, idleTime,contextTot,procTot,reads,writes;
    char bootTime[20];
    time_t boot;
    stdReport(rptFile, lineBuf);
    rptFile = fopen("/proc/stat", "r");
    fscanf(rptFile, "%*s%d%*d%d%d", &userTime, &sysTime, &idleTime);
    printf("Time spent in user mode(USER_HZ/Jiffies):\n%d\n\n", userTime);
    printf("Time spent in system mode(USER_HZ/Jiffies):\n%d\n\n", sysTime);
    printf("Time spent in idle mode(USER_HZ/Jiffies):\n%d\n\n", idleTime);
    while(fgets(lineBuf, LB_SIZE +1, rptFile) != NULL)
    {
        if(strncmp (lineBuf, "ctxt", 4) == 0)
        {
            sscanf(lineBuf,"%*s%d", &contextTot);
            printf("Total context switches made:\n%d\n\n", contextTot);
        }
        if(strncmp (lineBuf, "processes", 9) == 0)
        {
            sscanf(lineBuf, "%*s%d", &procTot);
            printf("Total number of processes created:\n%d\n\n", procTot);
        } 
        if(strncmp (lineBuf, "btime", 5) == 0)
            sscanf(lineBuf, "%*s%s", bootTime);
    }
    boot = strtoul(bootTime, NULL, 0);
    printf ("System boot time:\n%s\n", ctime(&boot));
    fclose(rptFile);
    rptFile = fopen( "/proc/diskstats", "r");
    while(fgets(lineBuf,LB_SIZE + 1, rptFile) !=NULL)
    {
        if (strstr(lineBuf, "sda") != NULL)
        {
            sscanf(lineBuf, "%*d%*d%*s%d%*d%*d%*d%d",&reads, &writes);
            break;
        }
    }
    printf("Total number of disk requests:\n%d\n\n", reads + writes);
    fclose(rptFile);
}

//generate a long report
void longReport(FILE *rptFile, char lineBuf[], int interval, int duration)
{
    int totalMem, availMem;
    int iteration = 0;
    shortReport(rptFile, lineBuf);
    rptFile = fopen("/proc/meminfo", "r");
    while(fgets(lineBuf,LB_SIZE + 1,rptFile) != NULL)
    {
        if(strncmp (lineBuf, "MemTotal", 8) == 0)
        {
            sscanf(lineBuf, "%*s%d", &totalMem);
            printf("Total memory(kb):\n%d\n\n", totalMem);
        }
        if(strncmp (lineBuf, "MemFree", 7) == 0)
        {
            sscanf(lineBuf, "%*s%d", &availMem);
            printf("Free memory(kb):\n%d\n\n", availMem);
        }
    }
    fclose(rptFile);
    printf ("Sampling load averages...\n");
    while(iteration < duration)
    {
        sleep(interval);
        sampleLoadAvg(rptFile, lineBuf);
        iteration += interval;
    }
    fclose(rptFile);
}

//convert seconds into DD:HH:MM:SS
void secondsFormat(int time)
{
    int minutes = time/60;
    int seconds = time%60;
    int hours = minutes/60;
    minutes = minutes %60;
    int days = hours /24;
    hours = hours %24;
    printf("%02d:%02d:%02d:%02d\n\n", days, hours, minutes, seconds);
}

//sample the load averagee
void sampleLoadAvg(FILE *rptFile, char lineBuf[])
{
    double average;
    rptFile = fopen("/proc/loadavg", "r");
    fgets(lineBuf, LB_SIZE + 1, rptFile);
    sscanf (lineBuf, "%lf", &average);
    printf("%4.2lf\n", average); 
}
