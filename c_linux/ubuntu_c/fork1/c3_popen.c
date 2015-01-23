/*************************************************************************
	> File Name: c3_popen.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Wed 10 Dec 2014 07:37:43 AM PST
 ************************************************************************/

#include<stdio.h>
#define MAXSTRS 5

int main()
{
	int cntr ;
	FILE * pipe_fp ;
	char *strings[MAXSTRS] = {"roy","zixia","guoki","supper","mmwan"};

	if((pipe_fp = popen("sort","w")) == NULL)
	{
		perror("popen");
		exit(1);
	}

	for(cntr = 0 ; cntr < MAXSTRS ; cntr++)
	{
		fputs(strings[cntr],pipe_fp) ;
		fputc('\n',pipe_fp);
	}

	pclose(pipe_fp);
	return 0 ;

}
