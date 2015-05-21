/*************************************************************************
	> File Name: main.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Thu 21 May 2015 06:34:32 AM PDT
 ************************************************************************/

#include<stdio.h>

//#include "add/add.h"
//#include "sub/sub.h"

#include "add.h"
#include "sub.h"

int main(void)
{
	int a = 10 ; 
	int b = 12 ;
	float x = 1.2345 ; 
	float y = 9.876 ;
	printf("int a + b is:%d\n",add_int(a,b));
	printf("int a - b is:%d\n",sub_int(a,b));
	printf("float x + y:%f\n",add_float(x,y));
	printf("float x - y:%f\n",sub_float(x,y));
	return 0 ;
}
