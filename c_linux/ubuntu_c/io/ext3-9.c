#include <stdio.h>
#include <dirent.h>
#include <unistd.h>

int main(int argc ,char *argv[])
{
	
	char path[1000] ;
	DIR *dp ;
	struct dirent *pdirent ;
	if( argc != 2)
	{	
		printf("Usage .\ext3-9 pathname");
		return 1 ;
	}
	if((dp = opendir(argv[1])) == NULL)
	{
		printf("Opendir %s failed \n" , argv[1]);
		return 2;
	}
	
	if((pdirent = readdir(dp)) == 0 )
	{
		printf("readdir %s failed \n", argv[1]);
		return 3 ;
	}
	printf("%s\n",pdirent->d_name);
	closedir(dp) ;
	
	return 0;
}
