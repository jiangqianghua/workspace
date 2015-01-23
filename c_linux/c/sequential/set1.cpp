#include <iostream>
#include <set>

using namespace std ;

void myprintf(set<int>::iterator begin, set<int>::iterator end);

int main()
{
	set<int> v;
	v.insert(1);
	v.insert(3);
	v.insert(5);
	v.insert(2);
	v.insert(4);
	v.insert(3);
	myprintf(v.begin(), v.end());
//	myprintf(v.rbegin(), v.rend());
	
	
}
void myprintf(set<int>::iterator begin, set<int>::iterator end)
{
	for ( set<int>::iterator it = begin ; it != end ; ++it )
	{
		cout << *it << " " ;
	}
	cout << endl ;
}
