#include "ExampleClass.h"

template<typename ElemType>
class MyRectangle:public Rectangle<ElemType>
{
public:
	void read(istream &in) ; 
	void display(ostream &out) const;
};


