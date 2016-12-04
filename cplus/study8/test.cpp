/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 25 Nov 2016 09:01:51 PM PST
 ************************************************************************/

#include<iostream>
using namespace std;


class Coordinate
{
	public:
		int x ;
		int y;
		void printX()
		{
			cout<<x<<endl ;		
		}
		void printY()
		{
			cout<<y<<endl;
		}
};





int main(void)
{
	Coordinate coor;
	coor.x = 10;
	coor.y = 20;
	coor.printX();
	coor.printY();

	Coordinate *p = new Coordinate();
	if(NULL == p)
	{
		return 1 ;
	}
	
	p->x = 100 ;
	p->y = 200 ;
	p->printX();
	p->printY();
	delete p;
	p = NULL ;

	return 0 ;
}
