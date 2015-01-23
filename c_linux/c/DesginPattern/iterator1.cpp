#include <iostream>
#include <vector>
using namespace std ;

template<class Item>
class Iterator1
{
public:
	virtual void first() = 0 ;
	virtual void next() = 0 ;
	virtual Item* currentItem() = 0 ;
	virtual bool isDone() = 0 ;
	virtual ~Iterator1(){};
};

template<class Item>
class ConcreteAggregate ;

template<class Item>
class ConcreteIterator : public Iterator1<Item>
{
private:
	ConcreteAggregate<Item> *aggr ;
	int cur ;
public:
	ConcreteIterator(ConcreteAggregate<Item>*a):aggr(a),cur(0){}
	virtual void first()
	{
		cur = 0 ;
	}
	virtual void next()
	{
		if(cur < aggr->getLen())
			cur++ ;
	}
	virtual Item* currentItem()
	{
		if(cur < aggr->getLen())
			return &(*aggr)[cur];
		else
			return NULL;
	}
	virtual bool isDone()
	{
		return (cur >= aggr->getLen());
	}

};


template<class Item>
class Aggregate
{
public:
	virtual Iterator1<Item>* createIterator() = 0 ;
	virtual ~Aggregate(){} ;
};

template<class Item>
class ConcreteAggregate : public Aggregate<Item>
{
private:
	vector<Item> data ;
public:	
	ConcreteAggregate()
	{
		data.push_back(1);
		data.push_back(2);
		data.push_back(3);
	}
	virtual Iterator1<Item> * createIterator()
	{
		return new ConcreteIterator<Item>(this);
	}
	Item& operator[](int index)
	{
		return data[index];
	}
	int getLen()
	{
		return data.size();
	}

};

int main()
{
	Aggregate<int> *aggr = new ConcreteAggregate<int>();
	Iterator1<int> *it = aggr->createIterator();
	for(it->first(); !it->isDone() ; ++it)
	{
		cout<<*(it->currentItem())<< endl;	
	}	
	delete it;
	delete aggr ;
	return 0 ;
}



