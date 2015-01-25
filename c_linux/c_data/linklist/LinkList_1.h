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
protected:
	NodePointer head ;
};

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
LinkList<ElemType>::NodePointer LinkList<ElemType>::getHead()
{
	return head ; 
}
