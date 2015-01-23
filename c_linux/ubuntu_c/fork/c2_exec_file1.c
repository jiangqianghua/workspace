/*************************************************************************
	> File Name: c2_exec_file.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 22 Nov 2014 11:21:06 PM PST
 ************************************************************************/

#include<stdio.h>
#include<fcntl.h>
#include<stdlib.h>

void failure(char *s)
{
	perror(s);
	exit(0);
}

printpos(char* string , int fildes)
{
	long pos ;
	if((pos = lseek(fildes,0L,1)) < 0L)
		failure("lseek failed");
	printf("%s : %ld\n" , string ,pos);
}

int main()
{
	int fd;
	int pid;
	char buf[5];
	if((fd = open("data",O_RDONLY))<0)
	{
		failure("open failed");
	}
	printf("excel fd = %d\n",fd);
	printpos("execl before",fd);
	read(fd,buf,5);
	printpos("execl after" , fd);
	return 0;
}

	
