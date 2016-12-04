/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 26 Nov 2016 06:38:20 AM PST
 ************************************************************************/

#include<iostream>
#include<string>
using namespace std;

class Teacher
{
	public:

		void setName(string _name);
		string getName();
		void setGender(string _gender);
		string getGender();
		void setAge(int _age);
		int getAge();
		void teach();
	private:
		string m_strName ; 
		string m_strGender ; 
		int m_iAge;
};
