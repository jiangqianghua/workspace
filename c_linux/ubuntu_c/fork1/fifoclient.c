/*************************************************************************
	> File Name: fifoclient.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Wed 10 Dec 2014 07:56:51 AM PST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>

#define FIFO_FILE "sampleFIFO"

int main(int argc, char *argv[])
{
	FILE *fp ;
	if(argc != 2)
	{
		printf("USAGE:fifoclient[string]\n");
		exit(0);
	}

	if((fp = fopen(FIFO_FILE,"w")) == NULL)
	{
		perror("fopen");
		exit(1);
	}

	fputs(argv[1],fp);
	fclose(fp);
	return 0 ;
}
