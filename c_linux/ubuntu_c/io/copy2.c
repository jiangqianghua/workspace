#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>

#define PERMS 0666
#define DUMMY 0
#define BUFSIZE 128
int main(int argc , char *argv[])
{
	int source_fd , target_fd , num ;
	char iobuffer[BUFSIZE] ;
	if( argc != 3)
	{
		printf("Usage : copy Sourcefile Targetfile\n");
		return 1;
	}
	
	if(( source_fd = open(*(argv+1), O_RDONLY,DUMMY)) == -1)
	{
		printf("Source file open error !\n");
		return 2;
	}
	printf("source_fd :%d\n", source_fd );	
	if(( target_fd = open(*(argv+2),O_WRONLY|O_CREAT, PERMS)) == -1 )
	{
		printf("Target file open error!\n");
		return 3;
	}
	printf("target_fd :%d\n" , target_fd);
	printf(" start copying ...\n");	
	while((num = read(source_fd , iobuffer , BUFSIZE)) > 0)
	{
		printf("%s",iobuffer);
		if( write(target_fd , iobuffer , num) != num )
		{
			printf("Target file wirte error ! \n");
			return 4 ;
		}
	}
	printf(" copy end... \n") ;
	close(source_fd) ;
	close(target_fd) ;
	return 0;

}
