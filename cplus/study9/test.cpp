/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 25 Nov 2016 09:31:44 PM PST
 ************************************************************************/

#include<iostream>
#include<string>
using namespace std;

int main(void)
{
	string name ; 
	cout<<"please input your name"<<endl ;
	getline(cin,name);
	if(name.empty())
	{
		cout<<"input is null"<<endl;
		return 1;
	}

	if(name == "imooc")
	{
		cout<<"you are a adminstrator"<<endl;
	}

	cout<<"hello" + name <<endl ;
	int size = name.size();

	cout<<"your name size is "<<size<<endl;
	cout<<"your first name is "<<name[0]<<endl;

	return 0;
}
