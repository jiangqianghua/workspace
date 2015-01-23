#include <iostream>
#include <vector>
#include <numeric>
#include <algorithm>
using namespace std ;

void myprintf(vector<int>::iterator begin,vector<int>::iterator end);
bool Comp(const int &a, const int &b);
int main()
{
	vector<int> v; 
	v.push_back(3);
	v.push_back(2);
	v.push_back(1);
	v.push_back(0);
	cout << " index " << v[3] << endl ;
	cout << " iterator " << endl ;
	myprintf(v.begin(),v.end());
	
	v.insert(v.begin(),8);
	v.insert(v.end(),10);
	myprintf(v.begin(),v.end());
	
	vector<int> arr(10);
	for ( int i = 0 ; i < 10 ; i++ )
	{
		arr[i] = i;
	}
	myprintf(arr.begin(),arr.end());
	// delete first
	arr.erase(arr.begin());
	myprintf(arr.begin(),arr.end());
	arr.erase(arr.begin(),arr.begin()+5);
	myprintf(arr.begin(),arr.end());
	reverse(v.begin(),v.end());
	myprintf(v.begin(),v.end());
	// sort
	cout << " sort " << endl ; 
	sort(v.begin(),v.end());
	myprintf(v.begin(),v.begin());
	vector<int> v1 ;
	v1.push_back(2);
	v1.push_back(1);
	v1.push_back(3);
	sort(v1.begin(),v1.end(),Comp);
	myprintf(v1.begin(),v1.end());
	return 0 ;
}

void myprintf(vector<int>::iterator begin,vector<int>::iterator end)
{
	for ( vector<int>::iterator i = begin ; i != end ; i++ )
	{
		cout << *i << " " ; 
	}
	cout<<endl; 
}
bool Comp(const int &a, const int &b)
{
	return a > b ;
}
