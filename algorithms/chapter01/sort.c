/*************************************************************************
	> File Name: sort.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Wed 11 Mar 2015 06:34:40 AM PDT
 ************************************************************************/

#include<stdio.h>
void insert(int * , int);
void display(int * , int);
int main()
{
	int arr[] = {2,4,1,6,4,7,9};
	int len = 7 ;
	int i = 0 ;
	printf("old num:\n");
	display( arr , len );
	printf("\n");
	insert(arr , len);
	printf("insert:\n");
	display( arr , len );
	return 0 ; 
}
void display(int *arr , int len)
{
	int i = 0 ; 
	for( ; i < len ; i++ )
	{
		printf("%d ," , arr[i]);
	}
}
void insert(int *arr , int len)
{
	int j = 1 ; 
	for ( ; j < len ; j++ )
	{
		int i = j - 1 ; 
		int key = arr[j] ;
		while ( i > -1 && arr[i] > key )
		{
			arr[i+1] = arr[i] ;
			i-- ;
		}
		arr[i+1] = key ;
	}
}
