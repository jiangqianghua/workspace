/*************************************************************************
	> File Name: client.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 20 Dec 2014 07:19:51 AM PST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<string.h>
#include<unistd.h>
#include<netdb.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<sys/types.h>
#include<arpa/inet.h>
#define MAX_BUF_SIZE 1024
void udpc_requ(int sockfd , const struct sockaddr *addr , socklen_t len)
{
	char buffer[MAX_BUF_SIZE] ;
	int n ; 
	while(fgets(buffer , MAX_BUF_SIZE , stdin))
	{
		sendto(sockfd , buffer ,strlen(buffer) , 0 ,addr , len) ;
		bzero(buffer , MAX_BUF_SIZE) ;
		n = recvfrom(sockfd , buffer , MAX_BUF_SIZE , 0 ,NULL,NULL) ;
		if( n <= 0 )
		{
			fprintf(stderr , "Recv Error %s\n" , strerror(errno)) ;
			return ;
		}
		buffer[n] = 0 ;
		fprintf(stderr , "get %s" , buffer) ;
	}
}

int main(int argc , char **argv)
{
	int sockfd , port ;
	struct sockaddr_in addr ; 
	if(argc !=3 )
	{
		fprintf(stderr , "Usage:%s server_ip server_port\n",argv[0]);
		exit(0) ;
	}

	if((port = atoi(argv[2])) < 0)
	{
		fprintf(stderr , "Usage:%s server_ip server_port\n",argv[0]);
		exit(1) ;
	}
	sockfd = socket(AF_INET , SOCK_DGRAM , 0) ;
	if(sockfd < 0 )
	{
		fprintf(stderr , "Socket Error:%s\n" , strerror(errno)) ;
		exit(1) ;
	}

	bzero(&addr , sizeof(struct sockaddr_in)) ;
	addr.sin_family = AF_INET ; 
	addr.sin_port = htons(port) ;
	if(inet_aton(argv[1] ,&addr.sin_addr) < 0)
	{
		fprintf(stderr , "Ip erro:%s\n" , strerror(errno)) ;
		exit(1) ;
	}
	if(connect(sockfd , (struct sockaddr *)&addr , sizeof(struct sockaddr_in)) == -1)
	{
		fprintf(stderr , "connect error %s\n" , strerror(errno)) ;
		exit(1) ;
	}
	udpc_requ(sockfd , &addr , sizeof(struct sockaddr_in)) ;
	close(sockfd) ;

}
