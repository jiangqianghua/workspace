/*************************************************************************
	> File Name: testGetOpt.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 13 Jun 2015 01:39:47 AM PDT
 ************************************************************************/

#include<stdio.h>
#include <getopt.h>

char *l_opt_arg ; 
char *const short_options = "n:b:l:";
struct option long_options[] = 
{
	{"name",required_argument,NULL,'n'}	,
	{"bf_name",0,NULL ,'b'},
	{"love",1, NULL,'l'},
	{0,0,0,0},
};

int main(int argc , char argv[])
{
	int c; 
	while((c = getopt_long(argc , argv , short_options,long_options,NULL))!= -1)
	{
		switch(c)
		{
			case 'n':
				printf("mu name is XL\n");
				break;
			case 'b':
				printf("his name is ST\n");
				break;
			case 'l':
				l_opt_arg = optarg ;
				printf("our love is %s\n",l_opt_arg);
				break;
		}
	}
	return 0;
}
