/*************************************************************************
	> File Name: c2_channel.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Wed 10 Dec 2014 06:15:44 AM PST
 ************************************************************************/

#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>

int main(void)
{
	int fd[2] , nbytes ;
	pid_t childpid ; 
	char string[] = "hello, world!\n";
	char readbuffer[80];

	pipe(fd);
	if((childpid = fork()) == -1)
	{
		perror("fork");
		exit(1);
	}
	if(childpid == 0)
	{
		close(fd[0]);
		write(fd[1],string,strlen(string));
		_exit(0);
	}
	else
	{
		close(fd[1]);
		nbytes = read(fd[0],readbuffer,sizeof(readbuffer));
		printf("REceivec string:%s\n",readbuffer);
	}

	return 0 ;
	
}
