/*************************************************************************
	> File Name: test.cpp
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sun 20 Nov 2016 12:27:47 AM PST
 ************************************************************************/

#include<iostream>
using namespace std;

int getMaxOrMin(int *arr , int count,bool isMax)
{
	int temp = arr[0];
	for(int i = 1;i<count;i++)
	{
		if(isMax)
		{
			if(temp<arr[i])
			{
				temp = arr[i];
			}
		}
		else
		{
			if(temp>arr[i])
			{
				temp = arr[i];
			}
		}
	}
	return temp ;
}

int main(void)
{
	int arr1[4] = {3,2,6,5};

	bool isMax = false ;
	cin>>isMax ; 
	cout<<getMaxOrMin(arr1,4,isMax)<<endl;
	int i;
	cin>>i;
}
