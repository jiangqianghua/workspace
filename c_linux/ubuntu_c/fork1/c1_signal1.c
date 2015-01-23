/*************************************************************************
	> File Name: c1_signal1.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Thu 27 Nov 2014 07:23:03 AM PST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<signal.h>

int main()
{
//	signal(SIGINT,SIG_IGN);
	printf("xixi\n");
	sleep(10);
	printf("end\n");
	return 0;
}
