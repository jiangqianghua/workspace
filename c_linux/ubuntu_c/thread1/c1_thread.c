/*************************************************************************
	> File Name: c1_thread.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 20 Dec 2014 09:37:35 PM PST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>

void *thrd_func(void *arg) ;
pthread_t tid ; 
int result ; 
int main()
{

	if((result = pthread_create(&tid,NULL,thrd_func,NULL)) != 0)
	{
		printf("Create thread error!\n") ;
//		exit(0) ;
		return -1;
	}

	printf("TID in pthread_create function:%u.\n" ,tid) ;
	printf("Main process:PID:%d , TID:%u.\n" , getpid() ,pthread_self()) ;
	sleep(1) ;
	return 0 ;
}

void *thrd_func(void *argv)
{
	printf("New process:PID :%d , TID :%u.\n" , getpid() , pthread_self()) ;
	printf("New process:PID :%d , TID :%u.\n" , getpid() , tid) ;
	pthread_exit(NULL) ;//退出线程
}
