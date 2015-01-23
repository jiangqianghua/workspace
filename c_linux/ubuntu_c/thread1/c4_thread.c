/*************************************************************************
	> File Name: c4_thread.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 20 Dec 2014 10:51:31 PM PST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>

#define THREAD_NUM 3
#define REPEAT_TIMES 5
#define DELAY 4

void *thrd_func(void *arg) ;

int main()
{
	pthread_t thread[THREAD_NUM] ;
	int no ;
	void *tret ;

	srand((int)time(0)) ; //初始化生成器

	for(no = 0 ; no < THREAD_NUM ; no++)
	{
		if(pthread_create(&thread[no] , NULL, thrd_func , (void *)no) != 0)
		{
			printf("Create thread %d error \n" , no) ;
			exit(1) ;
		}
		else
		{
			printf("Create thread %d success \n" , no) ;
		}
	}

	for(no = 0 ; no < THREAD_NUM; no++)
	{
		if(pthread_join(thread[no] , &tret) != 0)
		{
			printf("Join thread %d error \n" ,no) ;
			exit(1) ;
		}
		else
		{
			printf("Join thread %d success \n") ;
		}
	}

	return 0 ;
}


void *thrd_func(void *arg)
{
	int thrd_num = (void *) arg ;
	int delay_time = 0 ;
	int count = 0 ;
	printf("Thread %d is starting\n" , thrd_num) ;
	for(count = 0 ; count < REPEAT_TIMES ; count++)
	{
		delay_time = (int)(DELAY *(rand()/(double)RAND_MAX))+1 ;
		sleep(delay_time) ;
		printf("\t Thread %d job %d delay = %d.\n" , thrd_num ,count , delay_time) ;
	}
	printf("Thread %d is exiting .\n" , thrd_num) ;
	pthread_exit(NULL);
}
