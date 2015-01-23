#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>

void h_exit(int status)
{
	if(WIFEXITED(status))
		printf("normal termination,exit status=%d\n",WEXITSTATUS(status));
	else if(WIFSIGNALED(status))
		printf("abnormal termination,signal number=%d\n",WTERMSIG(status));
//	#ifdef WCOREDUMP
//		WCOREDUMP(status)?")":"(core file generated)");
//	#else
//		")");
//	#endif
}

int main()
{
	pid_t pid ;
	int status ;
	if((pid=fork())<0)
	{
		printf("fork error \n");
		exit(0);
	}
	else if(pid ==0 )
		exit(7);
	if(wait(&status)!=pid)
	{
		printf("wait error \n");
		exit(0);
	}
	h_exit(status);
	
	if((pid==fork())<0)
	{
		printf("fork error\n");
		exit(0);
	}
	else if(pid == 0)
		abort();
	
	if(wait(&status)!=pid)
	{
		printf("wait error.\n");
		exit(0);
	}
	h_exit(status);
	
	if((pid=fork())<0)
	{
		printf("fork error\n");
		exit(0);
	}
	else if(pid == 0)
		status/=0;
	if(wait(&status)!=pid)
	{
		printf("wait error.\n");
		exit(0);
	}
	h_exit(status);
	exit(0);
}
