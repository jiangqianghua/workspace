#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>

int main()
{
	pid_t pid ;
	if((pid=fork())<0)
	{
		printf("fork error \n");
		exit(0);
	}
	else if(pid == 0)
	{

		if((pid == fork())<0)
		{
			printf("fork error.\n");
			exit(0);
		}
		else if(pid>0)
			exit(0);
		sleep(2);
		printf("second child,parent pid=%d\n",getppid());
		exit(0);
	}

	if(waitpid(pid,NULL,0)!=pid)
	{

		printf("waitpid error\n");
		exit(0);
	}
	printf("exit....");
	exit(0);


}
