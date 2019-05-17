#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>

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
   host = argv[1];

   hp = gethostbyname(host);
   bzero((char*)&sin, sizeof(sin));
   sin.sin_family = AF_INET;
   bcopy(hp->h_addr,(char*)&sin.sin_addr, hp->h_length);
   
   //
    s = socket(PF_INET, SOCK_STREAM, 0);
    connect(s, (struct sockaddr*)&sin, sizeof(sin));
   
   //

   while(fgets(buf,sizeof(buf), stdin)){
      buf[MAX_LINE-1] = '\0';
      len = strlen(buf) + 1;
      send(s,buf,len,0);
   }
}
