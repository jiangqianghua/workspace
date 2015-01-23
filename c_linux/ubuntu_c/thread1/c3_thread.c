/*************************************************************************
		long mtype ;
	> File Name: c3_thread.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 20 Dec 2014 10:02:35 PM PST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>

void *thrd_func1(void *arg) ; 
void *thrd_func2(void *arg) ;

pthread_t tid1 , tid2;

int main()
{
	if(pthread_create(&tid1 , NULL , thrd_func1 , NULL) != 0)
	{
		printf("Create thread 1 error \n") ;
		exit(1) ;
	}

	if(pthread_create(&tid2 , NULL , thrd_func2 , NULL) != 0)
	{
		printf("Create thread 2 error \n") ; 
		exit(1) ;
	}

	if(pthread_join(tid1 ,NULL) != 0)
	{
		printf("Join thread 1 error ! \n") ;
		exit(1) ;
	}
	else
	{
		printf("Join thread 1 join ! \n") ;
	}

	if(pthread_join(tid2 , NULL) != 0)
	{
		printf("Join thread 2 error \n") ;
		exit(1) ;
	}
	else
	{
		printf("Thread 2 Joined! \n") ;
	}
	return 0 ;
}

void *thrd_func1(void *arg)
{
	pthread_setcancelstate(PTHREAD_CANCEL_ENABLE,NULL) ;

	while(1)
	{
		printf("Thread 1 is running! \n") ;
		sleep(1) ;
	}
	pthread_exit((void *) 0) ;
}

void *thrd_func2(void *arg)
{
	printf("Thread 2 is running! \n") ;
	sleep(5) ;
	if(pthread_cancel(tid1) == 0)
	{
		printf("Send cancel cmd to threas 1 .\n");
		sleep(1);
		pthread_exit((void *)0);	
	}
}
