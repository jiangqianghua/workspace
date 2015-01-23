#include <iostream>
#include <set>
#include <string>

using namespace std ;
struct Info
{ 
        string name ;
        double score ;
        bool operator < (const Info &a) const
        {
                return a.score < score ;
        }
};
void myprintf(set<Info>::iterator begin, set<Info>::iterator end);
int main()
{
	set<Info> s;
	Info info ;
	
	info.name = "abc";
	info.score = 123.3 ;
	s.insert(info);
	
	info.name = "EDF" ;
	info.score = -23.4 ;
	s.insert(info);
	
	info.name = "xyw" ;
	info.score = 73.3 ;
	s.insert(info) ;

	myprintf(s.begin(), s.end());

}
void myprintf(set<Info>::iterator begin, set<Info>::iterator end)
{
	for ( set<Info>::iterator it = begin ; it != end ; ++it )
	{
		cout << (*it).name << ":" <<(*it).score << endl ;
	}
	cout << endl ;
}
