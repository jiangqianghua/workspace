/*************************************************************************
	> File Name: mainServer.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 16 May 2015 07:17:22 PM PDT
 ************************************************************************/
//  how run mainServer
// ./mainServer port
#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<sys/socket.h>
#include<netdb.h>
#include<netinet/in.h>
#include<sys/types.h>
#include<arpa/inet.h>
#include<pthread.h>
#include"mainServer.h"
#include"tools.h"


// define client socket 
typedef struct SocketClient
{
	int client_fd ; 
	struct sockaddr_in client_addr;
} socketClient ;

// socket array
socketClient *clientArray ;// = (socketClient *)malloc(128*sizeof(socketClient)); 
int main(int argc , char *argv[])
{
	if(argc != 2)
	{
		fprintf(stderr ,"Usage: ./mainServer port\n");
		return -1 ;
	}
	// init params
	port = atoi(argv[1]);
//	printf("port =%d" , port);

	clientArray = (socketClient *)malloc(128*sizeof(socketClient)); 
	startServer();
	return 0 ;
}

/**
 *  start server
 * */
int startServer()
{
	int sockfd ;
	struct sockaddr_in server_addr ;
	int sin_size ;

	//  get sockfd 
	if((sockfd = socket(AF_INET, SOCK_STREAM , 0)) == 0)
	{
		fprintf(stderr , "create socket  error \n");
		return -1;
	}

	bzero(&server_addr , sizeof(struct sockaddr_in)) ;
	server_addr.sin_family = AF_INET;
	server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	server_addr.sin_port = htons(port);

	if(bind(sockfd , (struct sockaddr *)(&server_addr) ,sizeof(struct sockaddr)) == -1)
	{
		fprintf(stderr , "Bind error\n ");
		return -1;
	}
	
	if(listen(sockfd , 5) == -1)
	{
		fprintf(stderr , "Listen error\n");
		return -1 ;
	}
	sin_size = sizeof(struct sockaddr_in) ;
//	int count = 0 ;
	printf("start accept...\n");
	while(1)
	{
		printf(" \n-----------listen %d client\n",count);
		socketClient  sockClient ;
		if((sockClient.client_fd = accept(sockfd , (struct aockaddr*)(&sockClient.client_addr),&sin_size)) == -1)
		{
			fprintf(stderr, "accept error\n");
			return -1 ;
		}
		printf("count:%d come\n",count);
		clientArray[count] = sockClient ;
		count++ ;	
		pthread_t tid ;
		if((pthread_create(&tid , NULL, startClientChatThread , (void *)sockClient.client_fd)) != 0)
		{
			fprintf(stderr , "create thread error \n") ;
			return -1 ;
		}
		sleep(1);
	}

}

void *startClientChatThread(void *argv)
{
	int fd = (void *) argv;
	printf(" client come %d \n" , fd);
	char buffer[1024];
//	int recvn = 0 ;
	while(1)
	{
		int n = 0; 
		printf("wait reding\n");
		memset(buffer , 0 ,sizeof(buffer));
		n=read(fd,buffer,1024);
		printf(" n = %d" , n) ;
		if( n == 0)
		{
			//exit thread
			pthread_exit(NULL);
			return ;
		}
		printf("%s --- mybuffer\n", buffer);
		int i = 0 ;
		for(i = 0 ; i < count ; i++)
		{
			socketClient sockClient = clientArray[i];
			int client_fd = sockClient.client_fd ;
			write(client_fd , buffer ,n);
		}
	}
	
}
