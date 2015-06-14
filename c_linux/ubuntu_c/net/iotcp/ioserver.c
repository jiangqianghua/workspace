/*************************************************************************
	> File Name: ioserver.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sun 14 Jun 2015 02:03:41 AM PDT
 ************************************************************************/

#include<stdio.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<time.h>
#include<string.h>
#include<unistd.h>
#include<pthread.h>
#include<sys/select.h>

#define BUFFLEN 1024
#define SERVER_PORT 8886
#define BACKLOG 5
#define CLIENTNUM 1024
int connect_host[CLIENTNUM];

int connect_number = 0 ; 

static void *handle_request(void *argv)
{
	time_t now ; 
	char buff[BUFFLEN] ;
	int n = 0 ;
	int maxfd = -1 ; 
	fd_set scanfd ;
	struct timeval timeout ; 
	timeout.tv_sec = 1 ; 
	timeout.tv_usec = 0 ;

	int i = 0 ;
	int err = -1 ; 

	for(;;)
	{
		maxfd = -1 ; 
		FD_ZERO(&scanfd);
		for(i = 0 ; i < CLIENTNUM ; i++)
		{
			if(connect_host[i] != -1)
			{
				FD_SET(connect_host[i] , &scanfd);
				if(maxfd < connect_host[i])
				{
					maxfd = connect_host[i];
				}
			}
		}
			
			err = select(maxfd + 1 , &scanfd , NULL, NULL ,&timeout);
			switch(err)
			{
				case 0 :
					break ; 
				case -1:
					break ;
				default:
					if(
							connect_number <= 0)
						break ; 
					for(i = 0 ; i < CLIENTNUM ;  i++)
					{
						if(connect_host[i] != -1)
						{
							if(FD_ISSET(connect_host[i],&scanfd))
							{
								memset(buff , 0 , BUFFLEN); 
								n = recv(connect_host[i], buff , BUFFLEN , 0);
//								if(n > 0 && !strncmp(buff , "TIME" , 4))
//								{
								
									memset(buff , 0, BUFFLEN );
									now = time(NULL);
									sprintf(buff , "%24s\r\n",ctime(&now));
									send(connect_host[i],buff , strlen(buff),0);
//								}
								connect_host[i] = -1 ; 
								connect_number-- ; 
								close(connect_host[i]);
							}
						}
					}
					break ;
				}

	}
	printf("end request\n");
	return NULL;
}

static void *handle_connect(void *argv)
{
	int s_s = *((int *)argv) ;
	struct sockaddr_in from ; 
	socklen_t len = sizeof(from);

	for(;;)
	{
		int i = 0 ;
		printf("wait client connect...\n");
		int s_c = accept(s_s , (struct sockaddr*)&from , &len);
		
		printf("a client connet , from:%s\n",inet_ntoa(from.sin_addr));

		for(i = 0 ;i < CLIENTNUM ; i++)
		{
			if(connect_host[i] == -1)
			{
				connect_host[i] = s_c ; 
				connect_number++ ; 
				break;
			}
		}

	}
	return ;
}
int main(int argc , char *argv[])
{
	int s_s ; 
	struct sockaddr_in local ; 
	int i = 0 ;
	memset(connect_host , -1 , CLIENTNUM);
	s_s = socket(AF_INET,SOCK_STREAM,0);
	
	memset(&local , 0 , sizeof(local));
	
	local.sin_family = AF_INET ; 
	local.sin_addr.s_addr = htonl(INADDR_ANY);
	local.sin_port = htons(SERVER_PORT);

	bind(s_s , (struct sockaddr*)&local , sizeof(local));

	listen(s_s , BACKLOG) ;

	pthread_t thread_do[2] ; 

	pthread_create(&thread_do[0],NULL, handle_connect,(void*)&s_s);

	pthread_create(&thread_do[1],NULL, handle_request,NULL);

	for(i = 0 ; i < 2 ; i++)
	{
		pthread_join(thread_do[i], NULL);
	}

	close(s_s);

	return 0 ;
}
