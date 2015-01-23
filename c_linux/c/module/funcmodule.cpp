#include <iostream>
using namespace std ;

template <class T>
T min1(T x, T y) ;

int main()
{
	int i = 1 , j = 2;
	double m = 1.0 , n = 2.5 ;
	string str1 = "abc" , str2 = "b";
	cout << min1(i,j) << endl ;	
	cout << min1(m,n) << endl ; 	
	cout << min1(str1,str2) << endl ; 	
}

template <class T>
T min1(T x, T y)
{
	return x < y ? x:y ;
}
