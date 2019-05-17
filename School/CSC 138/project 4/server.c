/*Warren Quattrocchi
 * CSC 138
 * C socket
 * server.c
 */
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdlib.h>

#define SERVER_PORT 6512
#define MAX_PENDING 5
#define MAX_LINE 256

int main()
{
   struct sockaddr_in sin;
   char buf[MAX_LINE];
   int len;
   int s, new_s;
   
   bzero((char*)&sin,sizeof(sin));
   sin.sin_family = AF_INET;
   sin.sin_addr.s_addr = INADDR_ANY;
   sin.sin_port = htons(SERVER_PORT);

   //create a new socket
   if((s = socket(AF_INET,SOCK_STREAM,0)) < 0)
   {
      printf("Error creating socket\n");
      exit(0);
   }
   //bind the socket
   if((bind(s,(struct sockaddr *)&sin, sizeof(sin))) < 0)
   {
      printf("Error binding socket\n");
      exit(0);
   }
   //listen for incoming connections  
   if((listen(s,MAX_PENDING)) < 0)
   {
      printf("Error listening\n");
      exit(0);
   }
   //loop forever
   while(1){
      //accept incoming connection
      if((new_s = accept(s,(struct sockaddr *)NULL,NULL)) < 0)
      {
         printf("Error on accept\n");
         exit(0);
      }
      bzero(buf, MAX_LINE);
      len = sizeof(sin);
      //loop while client is still sending
      while(recv(new_s, buf, MAX_LINE,0) > 0)
          printf("%s", buf);
      close(new_s);
   }
}
