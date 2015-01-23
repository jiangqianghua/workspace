#include <stdio.h>
#include <sys/types.h>
#include <utime.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(int argc , char *argv[])
{

	int i ,fd ;
	struct stat statbuf ;
	struct utimbuf times ;
	if(argc != 2)
	{
		printf("Usage:utimefile filename\n");
		return 1 ;
	}
	
	if( (fd = open(argv[1],O_RDWR)) < 0 )
	{
		printf("%s open failed!" , argv[1]);
		return 2 ; 	
	}

	if( stat(argv[1] , &statbuf) < 0 )
	{
		return 3 ;
	}
	
	if( ftruncate(fd , 0) < 0 )
	{
		printf("%s truncate failed . \n", argv[1]);
		return 4 ;
	}

	printf("%s is truncated now .\n", argv[1]);
	times.actime = statbuf.st_atime ;
	times.modtime = statbuf.st_mtime ;
	
	if( utime(argv[1] , &times) == 0)
		printf("utime call successful \n");
	else
		printf("Error:utime call failed. \n");
	
	return 0;


}
