#include <vector>
#include <stdexcept>
#include "stack1.h"
using namespace std;

template<typename T>
void Stack1<T>::push(T const& elem)
{
	elems.push_back(elem);
}

template<typename T>
void Stack1<T>::pop ()
{
	if(elems.empty())
	{
		throw std::out_of_range("Stack<>::pop(): empty stack");
	}
	elems.pop_back();
}

template<typename T>
T Stack1<T>::top() const
{
	if(elems.empty())
	{
		throw std::out_of_range("Stack<>::top():empty stack");
	}
	return elems.back();
}

