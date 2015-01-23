#include "ExampleClass.h"
#include <iostream>
using namespace std; 
template <typename ElemType>
Rectangle<ElemType> Rectangle<ElemType>::operator=(Rectangle<ElemType> rightR)
{
	if(this != &rightR)
	{
		this->length = rightR.length ;
		this->width = rightR.width ; 
		myNo = rightR.myNo ;
		cout<<"operater = to length:"<<this->length << " width:" << this->width << endl;
	}
	return *this ;
}

template<typename ElemType>
void Rectangle<ElemType>::setLength(ElemType l)
{
	this->length = l;
}

template<typename ElemType>
void Rectangle<ElemType>::setNo(int i)
{
	this->myNo = i ;
}

template<typename ElemType>
Rectangle<ElemType>::Rectangle()
{
	this->length = this->width = 0 ;
	cout<<"structor null ..."<<endl;
}

template<typename ElemType>
Rectangle<ElemType>::Rectangle(const Rectangle<ElemType> & otherD)
{
	length = otherD.length ; 
	width = otherD.width ;
	myNo =otherD.myNo ;
}

template<typename ElemType>
Rectangle<ElemType>::~Rectangle()
{
	cout<<" over..." <<endl ; 
}
