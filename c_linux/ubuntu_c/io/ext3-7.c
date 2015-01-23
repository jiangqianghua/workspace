#include <stdio.h>
#include <unistd.h>
#include <limits.h>

int main(int argc , char *argv[])
{
	char path[1000] ;
	if(argc != 2)
	{
		printf("Usage ext3-7 pathname \n");
		return 1;
	}
	if(chdir(argv[1]) < 0)
	{
		printf("chdir failed \n");
		return 2;
	}
	if(getwd(path) == NULL)
	{
		printf("getwd failed \n");
		return 3;
	}
	printf("CED=%s\n" , path);
	return 0;

}
