/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Thu 22 Jan 2015 08:22:10 AM PST
 ************************************************************************/
// >> g++ -o test.so -shared -fPIC test.cpp
#include<iostream>
using namespace std;
void foo(int a , int b)
{
	cout<<a<<" "<<b<<endl ;
}
extern "C"
{
	void cfoo(int a, int b)
	{
		foo(a , b);
	}
}
