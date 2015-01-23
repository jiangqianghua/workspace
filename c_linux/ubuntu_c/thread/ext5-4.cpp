#include<stdio.h>
#include<sys/types.h>
#include<unistd.h>

int global = 4 ; 

int main()
{
	pid_t pid ; 
	int vari = 5 ; 
	if( (pid = vfork()) < 0 )
	{
		printf("vfork error. \n");
		return 1 ;
	}
	else if( pid == 0)
	{
		global++ ; 
		vari-- ;
		printf("Child change the vari and global %d  %d .\n" , global , vari);
		_exit(0);
	}
	else
	{
		global++ ; 
		vari-- ;
		printf("Parent didn't changed the vari and global %d  %d.\n", global , vari);
		
	}
	return 0 ; 
}
