#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
int main()
{
	int fd ; 
	char buf[10] ; 
	fd = open("test.c",0);
	read(fd,buf,10);
	printf("=====output in main() ====\n%s\n",buf);
	execl("./test","./test",0);
	printf("exec error \n");
	return 0 ;
}
