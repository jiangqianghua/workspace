/*************************************************************************
	> File Name: mysocket.h
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 05 Jun 2015 07:37:42 AM PDT
 ************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include<strings.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<netinet/in.h>
#include<signal.h>

int create_client_socket_tcp(int port, char *ip)
{
	struct sockaddr_in server_addr ;
	int s ;
	s = socket(AF_INET , SOCK_STREAM , 0);

	if(s < 0)
	{
		printf("socket error\n");
		return -1 ;
	}

	bzero(&server_addr , sizeof(server_addr));

	server_addr.sin_family = AF_INET ; 
	server_addr.sin_port = htons(port);
	server_addr.sin_addr.s_addr = htonl(INADDR_ANY);

	inet_pton(AF_INET , ip , &server_addr.sin_addr);

	connect(s , (struct sockaddr*)&server_addr , sizeof(struct sockaddr));

	return s ;

}

void process_conn_client(int s)
{
	ssize_t size = 0;
	char buffer[1024];

	for(;;)
	{
		size = read(0,buffer , 1024);
		if(size > 0)
		{
			send(s , buffer , size , 0);
			size = recv(s , buffer , 1024 , 0);
			write(1 , buffer , size);
		}
	}
}

int create_server_socket_tcp(int port,int backlog)
{
	int ss  ;
	struct sockaddr_in server_addr ; 

	int err ;

	ss = socket(AF_INET , SOCK_STREAM , 0);

	if(ss < 0)
	{
		printf("socket error \n");
		return -1 ;
	}

	bzero(&server_addr , sizeof(server_addr));

	server_addr.sin_family = AF_INET ; 
	server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	server_addr.sin_port = htons(port);

	err = bind(ss , (struct sockaddr*)&server_addr , sizeof(server_addr));
	if(err <0 )
	{
		printf("bind error\n");
		return -2 ;
	}
	err = listen(ss , backlog);

	if(err <0 )
	{
		printf("listen error\n");
		return -3;
	}

	return ss ;
}

void process_conn_server(int s)
{
	ssize_t size = 0 ;
	char buffer[1024];

	for(;;)
	{
		size = recv(s , buffer , 1024 , 0);

		if(size == 0)
		{
			return ;
		}

		sprintf(buffer , "%d bytes altogether\n", size);
		send(s , buffer , strlen(buffer)+1 , 0 );
	}
}

void sig_proccess(int signo)
{
	printf("Catch a exit signal\n");
	_exit(0);
}

void sig_pipe(int sign)
{
	printf("Catch a sigpipe signal\n");
	_exit(0);
}
