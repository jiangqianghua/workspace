#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>

int main()
{
	int status ;
	if((status = system("date"))<0)
	{
		printf("system error.\n");
		exit(0);
	}	
	printf("exit status = %d \n",status);
	
	if((status = system("nosuchcommand"))<0)
	{
		printf("system error.\n");
		exit(0);
	}
	
	printf("exit status =%d \n",status);
	
	if((status = system("who;exit 44"))<0)
	{
		printf("system error");
		exit(0);
	}
	
	printf("exit status = %d\n",status);
	exit(0);
	
}
