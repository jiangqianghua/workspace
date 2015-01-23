/*************************************************************************
	> File Name: envirom1.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Thu 27 Nov 2014 06:49:41 AM PST
 ************************************************************************/

#include<stdio.h>
extern char ** environ;
int main()
{
	char ** env = environ ;
	while(*env)
	{
		printf("%s\n", *env++) ;
	}
	return 0;
}
