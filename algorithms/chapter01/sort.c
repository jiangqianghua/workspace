/*************************************************************************
	> File Name: sort.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Wed 11 Mar 2015 06:34:40 AM PDT
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
void insert(int * , int);
void display(int * , int);

void mergesort(int *num, int start , int end);
void merge(int *num , int start , int middle , int end);
int main()
{
	int arr[] = {2,4,1,6,4,7,9};
	int len = 7 ;
	int i = 0 ;
	printf("old num:\n");
	display( arr , len );
	printf("\n");
//	insert(arr , len);
	mergesort(arr , 0 , 6);
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

//----------------------merge-----------------
void mergesort(int *num, int start , int end)
{
	int middle ; 
	if(start<end)
	{
		middle = (start+end)/2 ;
		// sort left
		mergesort(num , start , middle);
		// sort right
		mergesort(num , middle+1 , end);
		// merge
		merge(num , start , middle , end);
	}
}

void merge(int *num , int start , int middle , int end)
{
	int n1 = middle - start + 1 ;
	int n2 = end - middle ;
	int *L = (int *)malloc(sizeof(int)*(n1+1));
	int *R = (int *)malloc(sizeof(int)*(n2+1));
	int i , j = 0 , k ;

	for( i = 0 ; i < n1 ; i++ )
	{
		*(L+i) = *( num + start + i);
	}
	*(L+n1) = 1000000000;

	for( i = 0 ; i < n1 ; i++ )
	{
		*(R+i) = *( num + middle + i + 1);
	}
	*(R+n1) = 1000000000;
	
	i = 0 ;
	for( k = start ; k <= end ; k++)
	{
		if(L[i] <= R[j])
		{
			num[k] = L[i];
			i++ ;
		}else
		{
			num[k] = R[j];
			j++ ;
		}
	}
	free(L);
	free(R);
}
