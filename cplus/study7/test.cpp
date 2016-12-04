/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 25 Nov 2016 05:38:39 AM PST
 ************************************************************************/

#include<iostream>
using namespace std;


int main(void)
{
	int *p = new int(20);
	if(NULL == p)
	{
		return 1;
	}
	cout<<*p<<endl;
	delete p;
	p= NULL;
	return 0;
}
