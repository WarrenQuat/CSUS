#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

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

   s = socket(AF_INET,SOCK_STREAM,0);
   bind(s,(struct sockaddr *)&sin, sizeof(sin));  
   listen(s,MAX_PENDING);
   len = sizeof(sin);
   while(1){
      new_s = accept(s,(struct sockaddr *)&sin,&len);
      recv(new_s, buf, MAX_LINE,0);
      printf("%s\n", buf);
   }
}
