/*************************************************************************
	> File Name: pause.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Mon 08 Dec 2014 07:17:59 AM PST
 ************************************************************************/

#include<stdio.h>
#include<unistd.h>
#include<signal.h>

#define TRUE 1
#define FALSE 0
#define BELLS "\007\007\007"

int alarm_flag = FALSE ;

void setflag()
{
	printf("set flag to true ...\n");
	alarm_flag = TRUE ;
}

int main(int argc , char * argv[])
{
	int nsecs ;
	int i ;
	if( argc < 2)
	{
		fprintf(stderr , "Usage:#minutes message\n");
		exit(1);
	}
	if((nsecs = atoi(argv[1]) * 10) <= 5)
	{
		fprintf(stderr , "Invalid time\n");
		exit(2);
	}
	/* 设定SIGALRM 的关联动作*/
	signal(SIGALRM ,setflag);

	alarm(nsecs) ;
	printf("pause wait ...\n");
	pause(); // 使用pause（）调用等待信号
	printf("wait over...\n");
	if(alarm_flag)
	{
		printf("bells\n");
		printf(BELLS);
		for( i = 2 ; i < argc ; i++)
		{
			printf("%s\n" ,argv[1]);
		}
	}
	return 1 ;
}

