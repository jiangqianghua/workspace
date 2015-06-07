/*************************************************************************
	> File Name: testServer.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 05 Jun 2015 08:15:58 AM PDT
 ************************************************************************/

#include<stdio.h>
#include"mysocket.h"

int main(int argc , char *argv[])
{
	pid_t pid ;

	int ss , sc;
	struct sockaddr_in client_addr ;
	
	signal(SIGINT ,sig_proccess);
	signal(SIGPIPE,sig_proccess);
	ss = create_server_socket_tcp(8888,2);

	if(ss < 0)
		return -1 ;

	for(;;)
	{
		int addrlen = sizeof(struct sockaddr);

		sc = accept(ss , (struct sockaddr*)&client_addr , &addrlen);

		if(sc < 0)
			continue ;

		pid = fork();
		if(pid == 0)
		{
			close(ss);
			process_conn_server(sc);
		}
		else
		{
			close(sc);
		}

	}
}
