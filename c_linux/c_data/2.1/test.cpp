#include "ExampleClass.cpp"
#include "myExampleClass.cpp"
#include <iostream>
using namespace std;

int main()
{
	Rectangle<int> *ex = new Rectangle<int>();
	MyRectangle<int> *myex = new MyExtangle<int>();
	return 0 ;
}
