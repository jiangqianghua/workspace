#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

#define BLOCK 0 
#define BUFSIZE 1024

char *validID[] = {"123\n","567\n", "007\n", "421\n","F"};
int main(int argc , char *argv[])
{
	int fd , flags ;
	int n ;
	char userno[BUFSIZE], **ptr ;
	ptr = validID ;
	for ( n = 0 ; n < BUFSIZE ; n++ )
	{
		userno[n] = '\0' ;
	}
	setbuf(stdout , (char*)NULL) ;
	
	if(( fd = open("/dev/tty",O_RDONLY|O_NDELAY)) == -1)
	{
		printf("open error!\n");
		return 1 ;
	}	
	
	printf("Enter your userId :\n");
	sleep(4);
	if( read(fd , userno , BUFSIZE) == -1)
	{
		printf(" bye bye!\n");
		return 2 ;
	}
	
	while (( strcmp(*ptr , userno , 3) != 0 ) && ( strcmp(*ptr , "F") != 0))
	{
		++ptr ;
	}
	if(strcmp(*ptr,"F") == 0)
	{
		printf("Invalid user ID\n");
		return 3 ;
	}
	
	flags = fcntl(fd , F_GETFL) ;
	flags &= BLOCK ;
	flags &= fcntl(fd , F_SETFL , flags ); 
	printf("Enter your department number:\n");
	n = read(fd , userno , BUFSIZE );
	printf("\n weclome to department #");
	write(1 , userno , n) ;
	close(fd);
	
	return 0 ;

}
