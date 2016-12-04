/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 26 Nov 2016 06:38:20 AM PST
 ************************************************************************/

#include<iostream>
#include"teacher.h"
using namespace std;

void Teacher::setName(string _name)
{
	m_strName = _name;
}

string Teacher::getName()
{
	return m_strName ;
}

void Teacher::setGender(string _gender)
{
	m_strGender = _gender ;
}

string Teacher::getGender()
{
	return m_strGender ;
}

void Teacher::setAge(int _age)
{
	m_iAge = _age ; 
}

int Teacher::getAge()
{
	return m_iAge ;
}

void Teacher::teach()
{
	cout<<"start class"<<endl;
}
