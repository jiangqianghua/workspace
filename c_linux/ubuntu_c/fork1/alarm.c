/*************************************************************************
	> File Name: alarm.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Mon 08 Dec 2014 06:55:21 AM PST
 ************************************************************************/

#include<stdio.h>
#include<signal.h>

#define TIMEOUT 5
#define MAXTRIES 5
#define LINESIZE 100
#define BELL '\007'
#define TRUE 1
#define FALSE 0

static int time_out ;

static char inputline[LINESIZE];
char* quickreply(char* prompt);
void catch() ;
int main()
{
	printf("%s\n",quickreply("Input"));
}

char* quickreply(char* prompt)
{
	int (*was)(),ntries;
	char* answer ;

	was = signal(SIGALRM,catch);
	
	for(ntries = 0 ; ntries <MAXTRIES ; ntries++)
	{
		time_out = FALSE ;
		printf("\n%s",prompt);
		//设置定时器
		alarm(TIMEOUT);
		//获取输入
		answer = gets(inputline);
		//关闭定时器
		alarm(0);
		if(!time_out)
			break ;
	}

	//回复原有的sigalarm
	signal(SIGALRM,was);

	return (time_out?((char*)0):answer);
}

void catch()
{
	time_out = TRUE ;
	putchar(BELL);
	printf("bell");	
}
