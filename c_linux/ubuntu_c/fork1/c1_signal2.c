/*************************************************************************
	> File Name: c1_signal2.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Thu 27 Nov 2014 07:27:17 AM PST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<signal.h>

void catcher(int sig);
int main()
{
	signal(SIGINT,catcher);
	printf("xixi\n");
	sleep(10);
	printf("end\n");
	return 0;
}

void catcher(int sig)
{
	printf("Catch succeed\n");
//	return 1 ;
}
