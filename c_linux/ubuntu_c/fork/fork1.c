/*************************************************************************
	> File Name: fork1.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 22 Nov 2014 07:54:25 PM PST
 ************************************************************************/

#include<stdio.h>
#include <unistd.h>

int main()
{
	char* av[] = {"ls" , "-l", NULL};
	pid_t  pid ;
	printf("Now only one process\n");
	printf("Calling for...\n");
	execv("/bin/ls",av);
	//	execl("/bin/ls", "ls" , "-l", NULL);
	pid = fork();
	if(!pid)
		printf("I am the child\n");
	else if(pid > 0)
		printf("I am the parent , child has pid:%d\n" , pid);
	else
		printf("Fork fail\n");
	return 0;
}
