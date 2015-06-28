/*************************************************************************
	> File Name: shttpd_worker.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sun 14 Jun 2015 07:16:11 AM PDT
 ************************************************************************/

#include "shttpd.h"

static int workersnum = 0 ; 
static struct worker_ctl *wctls = NULL;
extern struct conf_opts conf_para ;
pthread_mutex_t thread_init = PTHREAD_MUTEX_INITIALIZER ;

#define STATUS_RUNNING 1
#define STATUS_STOP 0
static int SCHEDULESTATUS = STATUS_RUNNING ;

int Worker_ScheduleRun(int ss)
{
	DBFPRINT("==>Worker_ScheduleRun\n");
	struct sockaddr_in client ;

	socket_t len = sizeof(client);
	Worker_Init();

	int i = 0 ;

	for(; SCHEDULESTATUS == STATUS_RUNNING;)
	{
		struct timeval tv ; 
		fd_set rfds ; 
		int retval = -1 ; 

		FD_ZERO(&rfds);
		FD_SET(ss , &rfds);

		tv.tv_sec = 0 ;
		tv.tv_usec = 500000 ;

		retval = select(ss+1,&rfds , NULL , NULL ,&tv);
		switch(retval)
		{
			case -1:
			case 0:
				continue ; 
				break ; 
			defaule:
				if(FD_ISSET(ss , &rfds))
				{
					in sc = accept(ss , (struct sockaddr*)&client , &len);
					printf("client commint\n");
					i = WORKER_ISSTATUS(WORKER_IDEL);
					if(i == -1)
					{
						i = WORKER_ISSTATUS(WORKER_DETACHED);
						if( i != -1)
							Worker_add(i);
					}
					if(i != -1)
					{
						wctls[i].conn.cs = sc ;
						pthread_mutex_unlocl(&wctls[i].opts.mutex);
					}
				}
		}

	}
	DBGPRINT("<== Worker_ScheduleRun\n");
	return 0 ;
}
