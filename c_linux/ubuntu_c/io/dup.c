#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>

int main(int argc , char *argv[])
{
	int fd ;
	if((fd = open(argv[1] ,O_WRONLY|O_CREAT , 0644)) == -1)
	{
		printf(" open file %s error!\n", argv[1]);
		return 1 ;
	}
	if( dup2(fd , STDOUT_FILENO) == -1)
	{
		printf("dup2 fd failed !\n");
		return 2 ;
	}
	
	printf(" dup2 success!\n 123");
	close(fd);
	return 0 ;
}
