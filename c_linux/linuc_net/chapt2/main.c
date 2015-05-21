/*************************************************************************
	> File Name: main.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Wed 20 May 2015 05:53:39 AM PDT
 ************************************************************************/

#include<stdio.h>
extern int StrLen(char *str) ;

int main(void)
{
	char src[] = "hello dymatic";
	printf("string length is%d\n",StrLen(src));
	return 0 ;
}
