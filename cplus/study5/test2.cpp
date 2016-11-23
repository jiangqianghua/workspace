/*************************************************************************
	> File Name: test2.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sun 20 Nov 2016 05:43:28 AM PST
 ************************************************************************/

#include<iostream>
using namespace std;

void fun(int &a , int &b);
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
	// 10,10
	int a = 3 ; 
	int *p = &a ;
	int *&q = p ; 

	*q = 5;
	cout<<a<<endl ;
	// 5
	
	int x = 10,y=20;
	fun(x,y);
	cout<<x<<","<<y<<endl;
	// 20,10
	return 0;
}


void fun(int &a,int &b)
{
	int c = 0 ; 
	c = a ; 
	a = b ;
	b = c ;
}
