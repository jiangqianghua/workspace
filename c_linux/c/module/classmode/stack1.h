#include <vector>
#include <stdexcept>
using namespace std;
template <typename T>
class Stack1
{
        private:
                std::vector<T> elems ;
        public:
                void push(T const&) ;
                void pop();
                T top() const ;
                bool empty() const
                {
                        return elems.empty();
                }
};
