/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 25 Nov 2016 05:29:22 AM PST
 ************************************************************************/

#include<iostream>
using namespace std;

void fun(int i,int j = 20 ,int k = 10);
inline void fun(double i,double j);
int main(void)
{
	fun(100);
	fun(100,200);
	fun(100,200,300);

	fun(100.0,200.0);
	return 0;
}

void fun(int i , int j , int k)
{
	cout<<i<<" "<<j<<" "<<k<<endl;
}

void fun(double i , double j)
{
	cout<<i<<"   "<<j<<endl;
}
