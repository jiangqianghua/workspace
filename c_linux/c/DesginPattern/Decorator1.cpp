#include <iostream>
using namespace  std ; 

class Component
{
public:
	virtual ~Component()
	{
		cout << "~Component" << endl ;
	}
	virtual void Operator() = 0;
protected:
	Component()
	{

		
	}

};

class ConcreteComponent : public Component
{
public :
	ConcreteComponent()
	{

	}
	~ConcreteComponent()
	{
		cout << "~ConcreteComponent" << endl ;
	}
	virtual void Operator()
	{
		cout << "orgal: ConcreteComponent " << endl ;
	}
};


class Dectorator : public Component
{
public:
	Dectorator(Component *com)
	{
		this->_com = com ;
	}
	void setComponent(Component *com)
	{
		this->_com = com ;
	}
	virtual ~Dectorator()
	{
		cout << "~Dectorator" << endl ;
		delete this->_com ;
		this->_com = NULL;
	}
	void Operator()
	{
	
	}
	
	
protected:
	Component* _com ;

};

class ConcreteDectoratorA : public Dectorator
{
public:
	ConcreteDectoratorA(Component *com):Dectorator(com)
	{
		
	}
	~ConcreteDectoratorA()
	{
		cout << "~Dectorator " << endl ;
		delete this->_com ;
		this->_com = NULL;
	}
	virtual void Operator()
	{
		this->_com->Operator();
		this->AddBehaveA();
	}
	void AddBehaveA()
	{
		cout << " Add ConcreteDectoratorA Behavor A" << endl ;
	}
	
	
};

class ConcreteDectoratorB : public Dectorator
{
public:
        ConcreteDectoratorB(Component *com):Dectorator(com)
        {

        }
        ~ConcreteDectoratorB()
        {
                cout << "~Dectorator " << endl ;
                delete this->_com ;
                this->_com = NULL;
        }
        virtual void Operator()
        {
                this->_com->Operator();
                this->AddBehaveB();
        }
        void AddBehaveB()
        {
                cout << " Add ConcreteDectoratorA Behavor B" << endl ;
        }


};

class DectoratorOnlyOne : public Component
{
public:
        DectoratorOnlyOne(Component *com):_com(com)
        {

        }
        ~DectoratorOnlyOne()
        {
                cout << "~DectoratorOnlyOne " << endl ;
               	delete this->_com ;
		this->_com = NULL;
        }
        virtual void Operator()
        {
                this->_com->Operator();
                this->AddBehave();
        }
        void AddBehave()
        {
                cout << " Add DectorAtorOnlyOne  Behavor " << endl ;
        }

private:
	Component *_com ;
};




int main()
{
	Component *pCom = new ConcreteComponent();
	Dectorator* pDec =  NULL ;
	pDec = new ConcreteDectoratorA(pCom) ;
	pDec = new ConcreteDectoratorB(pDec);
	pDec->Operator();


}
