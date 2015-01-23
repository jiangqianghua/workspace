#include <iostream>
#include "ClassTemplate.h"
using namespace std;

template<typename T1 , typename T2>
myClass<T1,T2>::myClass(T1 a, T2 b):I(a),J(b){}

template <typename T1 , typename T2>
void myClass<T1,T2>::show()
{
	cout << "I=" << I << " J=" << J << endl ;
}
