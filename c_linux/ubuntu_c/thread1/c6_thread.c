/*************************************************************************
	> File Name: c4_thread.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 20 Dec 2014 10:51:31 PM PST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<semaphore.h>

#define THREAD_NUM 3
#define REPEAT_TIMES 5
#define DELAY 4

sem_t sem[THREAD_NUM] ;

void *thrd_func(void *arg) ;

int main()
{
	pthread_t thread[THREAD_NUM] ;
	int no ;
	void *tret ;

	srand((int)time(0)) ; //初始化生成器
	
	//初始化线程信号量，均为0 
	for(no = 0 ; no < THREAD_NUM-1 ; no++)
	{
		sem_init(&sem[no], 0 ,0 ) ;
	}
	sem_init(&sem[2],0,1) ;
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
	// 逐个取消信号量
	for(no = 0 ; no < THREAD_NUM ; no++)
	{
		sem_destroy(&sem[no]) ;
	}
	return 0 ;
}


void *thrd_func(void *arg)
{
	int thrd_num = (void *) arg ;
	int delay_time = 0 ;
	int count = 0 ;
	// p 
	sem_wait(&sem[thrd_num]) ;

	printf("Thread %d is starting\n" , thrd_num) ;
	for(count = 0 ; count < REPEAT_TIMES ; count++)
	{
		delay_time = (int)(DELAY *(rand()/(double)RAND_MAX))+1 ;
		sleep(delay_time) ;
		printf("\t Thread %d job %d delay = %d.\n" , thrd_num ,count , delay_time) ;
	}
	printf("Thread %d is exiting .\n" , thrd_num) ;
	// V
	sem_post(&sem[(thrd_num+THREAD_NUM-1)%THREAD_NUM]) ;

	pthread_exit(NULL);
}
