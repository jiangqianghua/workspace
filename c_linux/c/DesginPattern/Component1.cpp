#include <iostream>
#include <string>
#include <list>
using namespace std ; 

class Component
{
protected:
	string name ;
public:
	Component(string name):name(name)
	{
		
	}
	virtual void AddComponent(Component *component){} 
	virtual void RemoveComponent(Component *component){}
	virtual void GetChild(int depth) {}
};

class Leaf : public Component
{
public :
	Leaf(string name):Component(name){} ;
	void AddComponent(Component *component)
	{
		cout << "Leaf can't add component" << endl;
	}
	
	void RemoveComponent(Component *component)
	{
		cout << "Leaf can't remove component" << endl ;
	}
	void GetChild(int depth)
	{
		string _tmpstring(depth,'-');
		cout << _tmpstring << name << endl;
	}

};

class Composite : public Component
{
private:
	list<Component*> _components ;
public:
	Composite(string name):Component(name){}
	void AddComponent(Component *component)
	{
		_components.push_back(component);
	}
	void RemoveComponent(Component *component)
	{
		_components.remove(component);
	}
	
	void GetChild(int depth)
	{
		string tmpstring(depth,'-');
		cout << tmpstring << name << endl;
		list<Component*>::iterator iter = _components.begin();
		for(; iter != _components.end(); iter++)
		{
			(*iter)->GetChild(depth + 2);
		}
	}	
	
};


int main()
{
	Composite *root = new Composite("root");
	Leaf *leaf1 = new Leaf("leaf1");
	Leaf *leaf2 = new Leaf("leaf2");
	root->AddComponent(leaf1);
	root->AddComponent(leaf2);
	
	Composite *lay2 = new Composite("layer2");
	Leaf *leaf4 = new Leaf("leaf4");
	lay2->AddComponent(leaf4);
	Composite *lay1 = new Composite("layer1");
	Leaf *leaf3 = new Leaf("leaf3");
	lay1->AddComponent(leaf3);
	lay1->AddComponent(lay2);
	
	root->AddComponent(lay1);
	
	root->GetChild(1);
	cout << endl; 
	lay1->GetChild(1);
	cout << endl ;
	lay2->GetChild(1);
	return 0 ;
}
