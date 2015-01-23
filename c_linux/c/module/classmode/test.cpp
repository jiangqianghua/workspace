#include "stack1.cpp"
#include <iostream>
#include <string>
#include <cstdlib>
using namespace std;
int main()
{
try{	
	Stack1<int> intStack ;
	Stack1<string> stringStack ;
	intStack.push(7);
	cout<< intStack.top() <<endl ;

	intStack.pop(); 

//	intStack.pop() ;
	stringStack.push("hello");
	cout << stringStack.top() << endl ;
	stringStack.pop();
	stringStack.pop();
}catch (exception const& ex)
{
	cerr << "Exception: " << ex.what() << endl;
	return EXIT_FAILURE ;
}
	return 0 ;
}
