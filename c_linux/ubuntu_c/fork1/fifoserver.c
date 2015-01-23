/*************************************************************************
	> File Name: fifoserver.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Wed 10 Dec 2014 07:51:27 AM PST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<sys/stat.h>
#include<unistd.h>
#include<linux/stat.h>

#define FIFO_FILE "sampleFIFO"

int main()
{
	FILE *fp ;
	char readbuf[80];
	umask(0);
	mknod(FIFO_FILE , S_IFIFO|0666, 0);
	while(1)
	{
		fp = fopen(FIFO_FILE,"r");

		fgets(readbuf , 80 , fp);
		printf("REceivec string :%s\n" , readbuf);
		fclose(fp);
	}
	return 0;
}
