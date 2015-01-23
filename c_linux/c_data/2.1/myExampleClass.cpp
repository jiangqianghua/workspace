#include "myExampleClass.h"
#include <iostream>
using namespace std ;

template<typename ElemType>
void MyRectangle<ElemType>::read(istream &in)
{	
	cout<<" input lenght:";
	in>> length ;
	cout <<"input width:";
	in>> width;
}

template MyRectangle<ElemType>::display(ostream& out) const
{
	out<< "no:" << this->myNo << "length :" << this->length << " width" << this->width << endl;
}
