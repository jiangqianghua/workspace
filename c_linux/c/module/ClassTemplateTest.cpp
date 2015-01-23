#include <iostream>
#include "ClassTemplate.cpp"
using namespace std ; 
int main()
{
	myClass<int,int> class1(3,5);
	class1.show();
	myClass<string,char> class2("abc",'a');
	class2.show();	
}
