/*************************************************************************
	> File Name: testcmp.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 11 Jul 2015 08:34:02 PM PDT
 ************************************************************************/

#include<stdio.h>
#include<strings.h>

int main(void)
{
	char *a = "method:GET";
	char *b = "GET";
	printf("%d",strcasecmp(a,b));
	
}

