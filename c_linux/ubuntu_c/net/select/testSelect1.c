/*************************************************************************
	> File Name: testSelect.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 13 Jun 2015 09:36:44 AM PDT
 ************************************************************************/

#include<stdio.h>
#include<sys/time.h>
#include<sys/types.h>
#include<unistd.h>
#include<fcntl.h>

int main(void)
{
	int rd = open("text.txt",O_RDONLY);
	
	struct timeval tv ; 
	int err ; 

//	FD_ZERO(&rd);
//	FD_SET(0,&rd);

	tv.tv_sec = 5 ;
	tv.tv_usec = 0 ;

	err = select(1 , &rd , NULL , NULL , &tv);

	if(err == -1)
		perror("select()");
	else if(err)
		printf("Data is available now\n");
	else
		printf("No data within five seconds\n");
	
	return 0 ;
}
