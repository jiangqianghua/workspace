/*************************************************************************
	> File Name: server.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 19 Dec 2014 08:08:34 AM PST
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

int main(int argc , char *argv[])
{
	int sockfd , new_fd ; 
	struct sockaddr_in server_addr ;
	struct sockaddr_in client_addr ;
	int sin_size , portnumber ;
	char hello[] = "hello ! are you fine?\n" ;

	if( argc !=2 )
	{
		fprintf(stderr , "Usage:%s portnumber\a\n" , argv[0]) ;
		exit(1) ;
	}

	if( (portnumber = atoi(argv[1])) < 0)
	{
		fprintf(stderr , "Usage:%s portnumber\a\n",argv[0]);
		exit(1);
	}
	/*服务器端开始建立socket描述符*/
	if((sockfd = socket(AF_INET , SOCK_STREAM , 0)) == -1)
	{
		fprintf(stderr , "Socket error:%s\n\a" , strerror(errno));
		exit(1) ;
	}

	/* 服务器端填充sockaddr结构*/
	bzero(&server_addr , sizeof(struct sockaddr_in));
	server_addr.sin_family = AF_INET; 
	server_addr.sin_addr.s_addr = htonl(INADDR_ANY) ;
	server_addr.sin_port = htons(portnumber) ;
	/* 绑定sockfd描述符*/
	if(bind(sockfd , (struct sockaddr *)(&server_addr) , sizeof(struct sockaddr)) == -1)
	{
		fprintf(stderr , "Bind error:%s\n\a",strerror(errno)) ;
		exit(1);
	}
	/* 监听socket描述符 */
	if(listen(sockfd, 5) == -1)
	{
		fprintf(stderr , "Listen error:%s\n\a" ,strerror(errno));
		exit(1);
	}

	while(1)
	{
		sin_size = sizeof(struct sockaddr_in) ;
		if((new_fd = accept(sockfd,(struct sockaddr* )(&client_addr) , &sin_size)) == -1)
		{
			fprintf(stderr , "Accept error:%s\n\a" , strerror(errno));
			exit(1);
		}

		fprintf(stderr , "Server get connection from %s\n" , inet_ntoa(client_addr.sin_addr)) ;
		if(write(new_fd , hello , strlen(hello)) == -1)
		{
			fprintf(stderr , "write Error:%s\n" ,strerror(errno));
			exit(1) ;
		}
		close(new_fd) ;
	}
	close(sockfd) ;
	exit(0);
}

int my_write(int fd , void *buffer , int length)
{
	int bytes_left ; 
	int written_bytes ; 
	char *ptr ; 
	ptr = buffer ; 

	bytes_left = length ; 

	while(bytes_left > 0)
	{
		written_bytes = write(fd , ptr , bytes_left) ;
		if(written_bytes <= 0)
		{
			if(errno == EINTR)
				written_bytes = 0 ;
			else
				return -1 ;
		}

		bytes_left -= written_bytes ; 
		ptr += written_bytes ;
	}
	return 0 ;
}

int my_read(int fd , void *buffer , int length)
{

	int bytes_left ; 
	int bytes_read ; 
	char *ptr ; 
	bytes_left = length ; 
	while(bytes_left > 0)
	{
		bytes_read = read(fd , ptr , bytes_read) ;
		if(bytes_read < 0 )
		{
			if(errno = EINTR)
				bytes_read = 0;
			else
				return -1 ;
		}
		else if(bytes_read == 0)
		{
			break ;
		}

		bytes_left -= bytes_read ;
		ptr += bytes_read ;
	}

	return length-bytes_left ; 
}
