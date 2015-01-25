/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 27 Dec 2014 04:14:06 AM PST
 ************************************************************************/
// run>> test.cpp LinkList.h myhead.h  -o test
// run>> ./test
#include<iostream>
#include "LinkList.h"
using namespace std;

int main()
{
	cout<<"ok ... "<<endl ;

	LinkList<int>* linkList = new LinkList<int>();
	for(int i = 0 ; i < 10 ; i++)
	{
		cout<<"input:"<<i<<endl;
		linkList->addElem(i);
	}

	cout<<"---------------"<<endl;
	int a ;
	linkList->getElem(5,a);
	cout<<a<<endl;
	cout<<"linkList size:" << linkList->getLength()<<endl ;
	return 0 ; 
}

