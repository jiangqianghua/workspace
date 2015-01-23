#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
int main(int argc , char *argv[])
{
	
	int i ;
	struct stat buf ;
	char *ptr ;
	for ( i = 1 ; i < argc ; i++)
	{
		printf("%s:",argv[i]);
		if(lstat(argv[i] , &buf) < 0)
		{
			printf("error !");
			continue ;
		}
		switch(S_IFMT & buf.st_mode)
		{
			case S_IFREG : ptr = "regual"; break  ;
			case S_IFDIR : ptr = "director" ; break ;
			case S_IFCHR : ptr = "character special" ; break ;
			case S_IFBLK :ptr = "block special" ; break ;
//			case S_IFFIFO : ptr = "fifo" ; break ;
//			case S_IFSLNK : ptr = "symbolic link " ; break;
			case S_IFSOCK :ptr = "socket" ; break ;
			default : ptr = "unknown mode " ;
		}
		printf("%s \n",ptr);
	}
	return 01 ;
}
