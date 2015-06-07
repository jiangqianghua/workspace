/*************************************************************************
	> File Name: testClient.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 05 Jun 2015 08:03:21 AM PDT
 ************************************************************************/

#include<stdio.h>
#include "mysocket.h"
int main(int argc , char *argv[])
{
	signal(SIGINT ,sig_proccess);
	signal(SIGPIPE ,sig_pipe);

	int s = create_client_socket_tcp(8888,argv[1]);
	if(s < 0)
		return -1 ;
	process_conn_client(s);
	close(s);
}
