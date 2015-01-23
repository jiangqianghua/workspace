#include <iostream>
#include <stack>
using namespace std ;
int main()
{
	stack<int> s;
	s.push(1);
	s.push(2);
	s.push(3);
	s.push(4);	
	s.push(5);
	
 	cout << s.size() << endl ;
	
	while(s.empty() != true)
	{
		cout << s.top() <<endl ;
		s.pop();
	}
	return 0 ;
}



