#include<iostream>
using namespace std ;
int* insertSort(int *data , int len); 
int main()
{
	int data[10] = {4,2,5,7,4,1,0,8,9,6};
	int *arr = insertSort(data,10);
	for ( int i = 0 ; i < 10 ; i++ )
	{
		cout << arr[i] << " " ;
	}
	return 0 ;

}

int* insertSort(int *data , int len)
{		
	int i = 1 ; 
	for (  ; i < len ; i++ )
	{		
		int j = i ;
		int key = data[i] ;
		for ( ; j > 0 && data[j-1] > key ; j-- )
		{
			data[j] = data[j-1] ;
		}
		data[j] = key ;
	}
	return data ;

} 
