#include <iostream>
#include <map>
#include <string>

using namespace std ;


void myprintf(map<string,double>::iterator begin, map<string,  
double>::iterator end);
int main()
{
	map<string,double> m ;
	m["li"] = 123.4 ;
	m["wang"] = 23.1 ;
	m["zhang"] = -21.9 ;
	m["abc"] = 12.1 ;
	myprintf(m.begin(),m.end());
	
}

void myprintf(map<string,double>::iterator begin, map<string, 
double>::iterator end){
	for ( map<string ,double>::iterator it = begin; it != end ; ++it )
	{
		cout << (*it).first << ":" << (*it).second << endl ;
	}
	cout << endl ;
}




