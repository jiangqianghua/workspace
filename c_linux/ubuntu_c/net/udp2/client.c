/*************************************************************************
	> File Name: client.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sun 07 Jun 2015 08:06:01 AM PDT
 ************************************************************************/

#include<stdio.h>
#include<sys/types.h>
#include<unistd.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<string.h>
#define PORT_SERV 8888
#define BUFF_LEN 256 
void udpclie_echo(int s , struct sockaddr *to);
int main(int argc , char *argv[])
{
	int s ; 
	struct sockaddr_in addr_serv;

	s = socket(AF_INET , SOCK_DGRAM , 0 );

	memset(&addr_serv , 0 ,sizeof(addr_serv)) ;
	addr_serv.sin_family = AF_INET ; 
	addr_serv.sin_addr.s_addr = htonl(INADDR_ANY);
	addr_serv.sin_port = htons(PORT_SERV);

	udpclie_echo(s , (struct sockaddr*)&addr_serv);

	close(s) ; 
	return 0 ;
}

void udpclie_echo(int s , struct sockaddr *to)
{
	char buff[BUFF_LEN] = "udp test";
	struct sockaddr_in from ; 
	socklen_t len = sizeof(*to);

	sendto(s , buff , BUFF_LEN , 0 , to , len);
	recvfrom(s , buff , BUFF_LEN , 0 , (struct sockaddr*)&from , &len);

	printf("recved:%s\n",buff);
}
