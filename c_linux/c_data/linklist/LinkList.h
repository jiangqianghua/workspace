/*************************************************************************
	> File Name: LinkList.h
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Thu 25 Dec 2014 07:57:22 AM PST
 ************************************************************************/

#include<iostream>
#ifndef MYHEAD_H
	#define MYHEAD_H
	#include "myhead.h"	
#endif
using namespace std;
/*
template<typename ElemType>
class LinkNode
{
	public:
		ElemType data ;
		LinkNode *next;
}
*/
template <typename ElemType>
class LinkList
{
public:
	   
	class LinkNode
	{
		public:
			ElemType data ; 
			LinkNode *next ;
	};
	
	typedef LinkNode * NodePointer ; 
	void adverse() ; //链表逆转
	void clear() ;  // 清空循环链表

	Status deleteElem(ElemType e) ;//删除循环链表第一次出现的值
	void deleteRepeat();// 删除重复的值
	Status getElem(int i , ElemType& e) ;// 获取指定的元素
	NodePointer getHead(); //获取头部指针
	int getLength();
	Status insert(int i , ElemType e) ;
	bool isEmpty();
	Status locationElem(ElemType e , Status (*compare)(ElemType , ElemType) , int & i);
	Status nextElem(ElemType e , ElemType& next_e);
	LinkList<ElemType> operator = (LinkList<ElemType> rightL);
	Status priorElem(ElemType e , ElemType & prior_e);
	LinkList();
	virtual ~LinkList();
	LinkList(const LinkList<ElemType>& otherL);
	Status addElem(ElemType e);
protected:
	NodePointer head ;
};

template<typename ElemType>
Status LinkList<ElemType>::addElem(ElemType e)
{
	NodePointer p = this->head ; 
	NodePointer s ;

	while(p != NULL && p->next != NULL)
	{
		p = p->next ;
	}
	
	s = new LinkNode();
	s->data = e ;
//	cout<<e<<endl;
	if(!p)
	{
		cout<<"--"<<e<<endl;
		this->head = s ;
		return OK;
	}	
	cout<<"---"<<e<<endl;
	p->next = s ;
	return OK

	
}
template<typename ElemType>
LinkList<ElemType>::LinkList(const LinkList<ElemType>& otherL)
{
	NodePointer p ;
	NodePointer op = otherL->head ; 
	NodePointer s;
	p = head = NULL;

	while(op)
	{
	
		if(!head)
			head = op ; 
		s = new LinkNode();

		s->data = op->data ;

		p = s ; 
		p = p->next ;
	}
	if(head)
		p->next = NULL; 
}
template<typename ElemType>
LinkList<ElemType>::~LinkList()
{
	clear();
}
template<typename ElemType>
LinkList<ElemType>::LinkList()
{
	this->head = NULL;
}

template<typename ElemType>
Status LinkList<ElemType>::priorElem(ElemType e , ElemType & prior_e)
{
	NodePointer pr ;
	NodePointer p ;

	p = pr = this->getHead();

	while(p && !equal(p->data , e))
	{
		pr = p;
		p = p->next;
	}

	if(!p || !pr)
		return ERROR ; 

	prior_e->data = pr->data ; 
	return OK;
}
template<typename ElemType>
LinkList<ElemType> LinkList<ElemType>::operator = (LinkList<ElemType> rightL)
{
	NodePointer p = NULL; 
	NodePointer rp = rightL->getHead();
	NodePointer s ; 
	if(this != &rightL)
	{
		clear();
		while(rp)
		{
			s = new LinkNode() ;
			s->Data = rp->Data ;
			if(!head)
				head = s ;
			else
				p->next = s ;

			p = s ;
			rp = rp->next ; 
		}

		if(p)
			p->next = NULL ;
	}

	return *this ;
}
template<typename ElemType>
Status LinkList<ElemType>::nextElem(ElemType e , ElemType& next_e)
{
	NodePointer p = head ; 
	while(p && !equal(p->data , e))
	{
		p = p->next ;
	}

	if(!p || !p->next)
		return ERROR ; 

	next_e = p->next->Data ;
	return OK ;
}
/*
template<typename ElemType>
Status compare(ElemType e1 , ElemType e2)
{
	return e1 == e2 ;
}
*/
template<typename ElemType>
Status LinkList<ElemType>::locationElem(ElemType e , Status (*compare)(ElemType , ElemType) , int & i)
{
	NodePointer p = head ; 
	i = 1; 
	while(p && !(&compare)(p->data , e))
	{
		i++ ; 
		p = p->next ;
	}

	if(!p)
		return ERROR;

	return OK;
}
template<typename ElemType>
bool LinkList<ElemType>::isEmpty()
{
	return head?false:true;
}
template<typename ElemType>
Status LinkList<ElemType>::insert(int i , ElemType e)
{
	int index = 0 ; 
	NodePointer p = head ; 
	NodePointer s ;

	while(p && index < i - 1)
	{
		index++ ;
		p = p->next ;
	}

	if(!p || index > i)
	{
		return ERROR ;
	}

	s = new LinkNode() ; 
	s->data = e ;

	if(i == 1)
	{
		s->next = head ; 
		head = s ;
	}
	else
	{
		s->next = p->next ; 
		p->next = s ;
	}
	return OK ;
}
template<typename ElemType>
int LinkList<ElemType>::getLength()
{
	int n = 0 ;
	NodePointer p = head;
	while(p != NULL)
	{
		p = p->next ;
		n++;
	}

	return n ;
}

template<typename ElemType>
void LinkList<ElemType>::adverse()
{
	NodePointer r , p ,q ;
	if(!head)
		return ; 
	r = NULL , p = head , q = p->next ; 
	while(p)
	{
		p->next = r ; 
		r = p ;
		p = q ; 
		if(q)
			q = q->next ;
	}
	head = r ; 
}
template<typename ElemType>
void LinkList<ElemType>::clear()
{
	NodePointer p ,q ;
	p = NULL ; q = head ;
	while(q)
	{
		p = q ; 
		q = q->next ; 
		delete p ;
	}
	head = NULL;
}

template<typename ElemType>
Status LinkList<ElemType>::deleteElem(ElemType e)
{
	NodePointer r , p ;
	r = NULL , p = head ; 
	while( p && p->data != e)
	{
		r = p ; 
		p = p->next ; 
	}

	if(p == NULL)
		return ERROR ; 
	if(r == NULL)
		head = head->next ; 
	else
		r->next = p->next ; 

	free(p) ;
	return OK ;
}

template<typename ElemType>
void LinkList<ElemType>::deleteRepeat()
{
	NodePointer r , p ;
	NodePointer s ;
	r = NULL , p = head ; 
	while(p)
	{
		s = head ; 
		while(s != p )
		{
			if(s->data == p->data)
			{
				r->next = p->next ; 
				delete p ; 
				p = r ; 
				break ;
			}

			s = s->next ;
		}
		r = p ;
		if(p)
			p = p->next ; 
	}
}

template<typename ElemType>
Status LinkList<ElemType>::getElem(int i , ElemType &e)
{
	int j = 1 ; 
	NodePointer p = head ; 
	while(p && j < i)
	{
		p = p->next ; 
		++j ;
	}

	if(p == NULL || j > i)
	{
		return ERROR ;
	}
	e = p->data ;
	return OK ;
}

template<typename ElemType>
typename  LinkList<ElemType>::NodePointer LinkList<ElemType>::getHead()
{
	return head ; 
}
