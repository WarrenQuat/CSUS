/*Warren Quattrocchi
 * CSC 138
 * C socket
 * client.c
 */

#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>
#include <stdlib.h>

#define SERVER_PORT 6512
#define MAX_LINE 256

int main(int argc, char * argv[])
{
   FILE *fp;
   struct hostent *hp;
   struct sockaddr_in sin;
   char *host;
   char buf[MAX_LINE];
   int s;
   int len;
   if(argc <2)
   {
      printf("usage: client [host ip]\n");
      exit(0);
   }
   host = argv[1];

   hp = gethostbyname(host);
   bzero((char*)&sin, sizeof(sin));
   sin.sin_family = AF_INET;
   bcopy(hp->h_addr,(char*)&sin.sin_addr, hp->h_length);
   sin.sin_port = htons(SERVER_PORT);
   
    //create new socket 
    if((s = socket(PF_INET, SOCK_STREAM, 0)) < 0)
    {
      printf("Error creating socket\n");
      exit(0);
    }
    //connect socket to server  
    if(connect(s, (struct sockaddr*)&sin, sizeof(sin)) < 0)
    {
      printf("Error connecting\n");
      exit(0);
    }
   //loop while input is given
   while(fgets(buf,MAX_LINE,stdin) > 0){
      buf[MAX_LINE-1] = '\0';
      len = strlen(buf) + 1;
      //send message to server
      if(send(s,buf,len,0) < 0)
      {
         printf("Error sending");
         exit(0);
      }
      //initialize buffer
      bzero(buf,MAX_LINE);
   }
}
