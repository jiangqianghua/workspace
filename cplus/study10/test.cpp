/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 26 Nov 2016 06:38:20 AM PST
 ************************************************************************/
//run   g++ test.cpp teacher.h teacher.cpp -o test
#include<iostream>
#include"teacher.h"
//using namespace std;

int main(void)
{
	Teacher t ; 
	t.setName("jiang");
	t.setGender("man");
	t.setAge(20);
	cout<<t.getName()<<" "<<t.getGender()<<" "<<t.getAge()<<" "<<endl;
	t.teach();
	return 0 ;
}
