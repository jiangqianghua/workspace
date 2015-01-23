#include <stdio.h>
#include <unistd.h>

int main()
{

	printf(" ==== system call execl testing ====\n");
	execl("ext5-1", "ext5-1" ,0);
	printf("exec error!\n");
	return 0 ;
}
