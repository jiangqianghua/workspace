/*************************************************************************
	> File Name: SqListTest.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 22 Nov 2014 01:07:09 AM PST
 ************************************************************************/

#include<iostream>
#include<string>
#include"SqList.hpp"
using namespace std;

int main()
{
	SqList<int> slist ;
	slist.insert(slist.getLength()+1,1);
	slist.insert(slist.getLength()+1,2);
	cout <<slist.getLength() << endl ;
	cout<<"-------------"<<endl;
	for( int i = 0 ; i < slist.getLength() ; i++)
	{
		int elem ;
		slist.getElem(i+1,elem) ;
		cout << elem << endl;
	}
	return 0;
}

