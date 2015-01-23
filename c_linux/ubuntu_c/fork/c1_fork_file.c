/*************************************************************************
	> File Name: c1_fork_file.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 22 Nov 2014 10:59:35 PM PST
 ************************************************************************/
// run gcc -o c1_fork_file c1_fork_file.c  文件共享 ，但变量不共享
#include<stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
void failure(char *s)
{
	perror(s);
	exit(1);
}

printpos(char* string , int fildes)
{
	long pos ;
	if((pos = lseek(fildes, 0L, 1)) < 0L)
		failure("lseek failed");
	printf("%s , %ld\n" , string ,pos);
}

int main()
{
	int fd ;
	int pid ;
	int mainid = 1;
	char buf[10];
	if((fd = open("data" ,O_RDONLY))<0)
		failure("open failed");
	read(fd,buf,10);
	printpos("Before fork",fd);
	if((pid = fork()) < 0)
		failure("fork failed");
	else if(!pid)
	{
		printf("child change before %d\n",mainid);
		mainid++;
		printf("child change after %d\n" , mainid);
		printpos("Child befor read",fd);
		read(fd , buf , 10);
		printpos("child after read",fd);
	}
	else
	{
		wait(NULL);
		printf("parent mainid=%d\n",mainid);
		printpos("parent atfer wait",fd);
		
	}
	return 0;
}
