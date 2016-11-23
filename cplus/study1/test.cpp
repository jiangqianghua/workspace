/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 19 Nov 2016 02:04:55 AM PST
 ************************************************************************/

#include<iostream>
#include<stdlib.h>
using namespace std;

int main(void)
{
	int x = 0 ; 
	cout<<"input:"<<endl;
	cin>>x;
	cout<<oct<<x<<endl;
	cout<<dec<<x<<endl;
	cout<<hex<<x<<endl;

	cout<<"input bool:"<<endl;
	bool y = false ;
	cin>>y;cout<<boolalpha<<y<<endl;
	system("pause");
}
