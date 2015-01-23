/*************************************************************************
	> File Name: exec.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 22 Nov 2014 10:17:04 PM PST
 ************************************************************************/
// run gcc -o exec exec.c
#include<stdio.h>
#include <unistd.h>
int main(int argc , char *argv[])
{
//	execvp(argv[1] , argv++);
//	char *av[] = {"ls","-l",NULL};
	argv++ ;
	execv("/bin/ls",argv);
	printf("error\n");
}

