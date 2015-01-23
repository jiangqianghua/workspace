/*************************************************************************
	> File Name: fork_exec.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 22 Nov 2014 10:37:03 PM PST
 ************************************************************************/

#include<stdio.h>
#include <unistd.h>

int main()
{
	int pid ; 
	pid = fork();
	switch(pid)
	{
		case -1:
			perror("fork failed\n");
		case 0:
//			execl("/bin/ls","ls","-1","--color" , NULL);
			execl("./exec" , "ls" , "-l","--color" , NULL);
			perror("execl failed\n");
		default:
			wait(NULL);
			printf("Is complete\n");
//			exit(0);
	}
	return 0;

}
