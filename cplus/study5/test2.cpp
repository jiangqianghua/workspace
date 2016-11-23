/*************************************************************************
	> File Name: test2.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sun 20 Nov 2016 05:43:28 AM PST
 ************************************************************************/

#include<iostream>
using namespace std;

typedef struct
{
	int x ;
	int y ;
} Coord;

int main(void)
{
	Coord c ;
	Coord &c1 = c;

	c1.x = 10;
	c1.y = 10;

	cout<<c.x<<"，"<<c.y<<endl;

	return 0;
}
