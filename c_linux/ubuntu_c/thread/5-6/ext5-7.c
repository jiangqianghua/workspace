#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(int argc , char *argv[])
{

	int fd ;
	int stat , pid ;
	struct stat stbuf ;
	time_t old_time = 0 ;
	if(argc != 3 )
	{
		fprintf(stderr,"Usage:%s watchfile copyfile \n",argv[0]);
		return 1;
	}
	if((fd = open(argv[1],O_RDONLY)) == -1)
	{
		fprintf(stderr , "Watchfile:%s can't open",argv[1]);
		return 2;
	}
	fstat(fd , &stbuf);
	old_time = stbuf.st_mtime ;
	printf("for ... \n");
	for(;;)
	{
		printf("for...\n");
		fstat(fd,&stbuf);
	//	printf("start for ...%d %d",old_time,stbuf.st_mtime);
		//fstat(fd,&stbuf);
		if(old_time != stbuf.st_mtime)
		{
			printf("fork...\n");
			while((pid=fork()) == -1);
			if(pid == 0)
			{
				execl("/bin/cp","/bin/cp",argv[1],argv[2]);
				return 3;	
			}
			printf("wait...\n");
			wait(&stat);
			old_time = stbuf.st_mtime ; 
		}
		else
			sleep(10);
	}
	return 0 ;
}
