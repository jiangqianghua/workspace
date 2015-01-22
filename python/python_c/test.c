/*************************************************************************
	> File Name: test.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Thu 22 Jan 2015 08:12:16 AM PST
 ************************************************************************/
// >> gcc -o -test.so -shared -fPIC test.c
#include<stdio.h>
int foo(int a , int b)
{
	printf("Your input %i and %i\n" , a , b);
	return a + b ;
}
