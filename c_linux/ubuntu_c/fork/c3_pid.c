/*************************************************************************
	> File Name: c3_pid.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sun 23 Nov 2014 04:09:47 AM PST
 ************************************************************************/

#include<stdio.h>
#include<unistd.h>

static int num = 0 ;
static char namebuf[20] ;
static char prefix[] = "/tmp/tmp";

itoa(int i , char *string)
{
	int power , j ;

	j = i ; 
	
	for(power = 1 ; j >= 10; j/=10)
		power*= 10;
	for(; power>0;power/=10)
	{
		*string++='0'+i/power ;
		i%=power ;
	}
	*string = '\0';

}

char* gettemp()
{
	int length , pid ;
	/* 获取进程标识符*/
	pid = getpid();
	printf("%d\n",pid);
	strcpy(namebuf , prefix) ;
	length = strlen(namebuf);
	itoa(pid , &namebuf[length]);
	strcat(namebuf , ".");
	do
	{
		itoa(num++ , &namebuf[length]);

	}while(access(namebuf,0)!=-1); /*检查文件是否存在 */
	return namebuf ;

}

int main()
{

	char *tempfile = gettemp() ;
	printf("%s\n" , tempfile);
}
