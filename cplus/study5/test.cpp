/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sun 20 Nov 2016 05:30:23 AM PST
 ************************************************************************/

#include<iostream>
using namespace std;


int main(void)
{
	int a = 10;
	int &b = a ;

	b = 20 ;

	cout<<a<<endl ;
	
	a = 60;

	cout<<b<<endl;
	int i = 0 ; 
	cin>>i ;

	return 0 ;
}
