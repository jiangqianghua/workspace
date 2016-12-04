/*************************************************************************
	> File Name: test2.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 26 Nov 2016 12:16:54 AM PST
 ************************************************************************/

#include<iostream>
using namespace std;

class Student
{
	public:
		void setName(string name)
		{
			m_strName = name ;	
		}

		string getName()
		{
			return m_strName ;
		}
		void setGender(string gender)
		{
			m_strGender = gender ;
		}

		string  getGender()
		{
			return m_strGender ;
		}
		
		void setScore(int score)
		{
			m_iScore = score ;
		}

		int getScore()
		{
			return m_iScore;
		}

		void initScore()
		{
			m_iScore = 0 ;
		}

		void study(int score)
		{
			m_iScore += score;
		}
	private:
		string m_strName ; 
		string m_strGender ;
		int m_iScore;
};

int main(void)
{
	Student stu ; 
	stu.initScore();
	stu.setName("xoaojiang");
	stu.setGender("female");
	stu.study(40);
	stu.study(70);

	cout<<stu.getName()<<" "<<stu.getGender()<<" "<<stu.getScore()<<endl;
	return 0;
}
