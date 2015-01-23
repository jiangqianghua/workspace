#include <vector>
#include <stdexcept>

template<typename T, typename CONT = std::vector<T> >
class Stack
{
	private:
		CONT elems ;
	public:
		void push(T const&) ;
		void pop();
		T top() const ;
		bool empty() const
		{
			return elems.empty();
		}
};
