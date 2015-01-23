#define LIST_MAX_SIZE	100
#define LISTINCREMENT 10
#define ERROR 0 ;
#define OK 1;
typedef int Status ;

template <typename ElemType>
class SqList
{
public:
	int bin_Search(ElemType key) ;
	void clear();
	Status deleteElem(int i , ElemType &e) ;
	Status getElem(int i , ElemType &e);
	int getLength();
	int getListSize();
	Status insert(int i , ElemType e);
	bool isEmpty();
	int locateElem(ElemType e ,Status (*compare)(ElemType , ElemType));
	Status nextElem(ElemType e , ElemType& next_e);
	SqList<ElemType> operator = (SqList<ElemType> rightL) ;
	Status priorElem(ElemType e , ElemType & prior_e);	
	int sequentialSearch(ElemType e);
	SqList();
	virtual ~SqList();
	SqList(const SqList<ElemType> &otherL) ;
protected:
	ElemType *elem ; 
	int listSize ; 
	int n ;
};

template<typename ElemType>
int SqList<ElemType>::bin_Search(ElemType key)
{
	int low , high , mid ;
	low = 0 ; 
	high = n - 1 ;
	while(low <= high)
	{
		mid = (low + high)/2 ;
		if(elem[mid] == key)
			return mid ; 
		else if(elem[key] > key)
			high = mid - 1;
		else
			low = mid + 1 ;
	}
	return 0 ;
}

template<typename ElemType>
void SqList<ElemType>::clear()
{
	n = 0 ;
}

template<typename ElemType>
Status SqList<ElemType>::deleteElem(int i , ElemType &e)
{
	if( i  > n || i < 1 )
		return ERROR ;
	e = elem[i-1];
	for( int j = i + 1 ; j <= n ; j++)
		elem[j-2] = elem[j-1] ;
	--n ; 
	return OK;
}
template<typename ElemType>
Status SqList<ElemType>::getElem(int i , ElemType &e)
{

	if(i > n || i < 1)
		return ERROR ;

	e = elem[i] ;
	return OK;
}

template<typename ElemType>
int SqList<ElemType>::getLength()
{
	return n ; 
}

template<typename ElemType>
int SqList<ElemType>::getListSize()
{
	return listSize ;
}

template<typename ElemType>
Status SqList<ElemType>::insert(int i , ElemType e)
{
	ElemType *newBase ;
	if( i > n+1 || i < 1)
		return ERROR ;
	if(n >= listSize)
	{
		newBase = new ElemType[listSize + LISTINCREMENT];
		for( int j = 1 ; j <= n ; j++)
			newBase[j-1] = elem[j-1];
		delete[] elem ;
		elem = newBase ;
		listSize += LISTINCREMENT ;
	}	
	for(int j = n ; j >= i ; j--)
	{
		elem[j] = elem[j-1] ;
	}
	elem[i-1] = e ;
	++n ; 
	return OK;
}

template<typename ElemType>
bool SqList<ElemType>::isEmpty()
{
	if( n == 0)
		return true ; 
	return false ;
}

template<typename ElemType>
int SqList<ElemType>::locateElem(ElemType e , Status (*compare)(ElemType , ElemType))
{
	int i ;
	for( i = 1 ; i <= n && !(*compare)(elem[i-1],e); ++i);
	
	if(i < n)
		return i ; 
	else 
		return 0 ;
}

template<typename ElemType>
Status SqList<ElemType>::nextElem(ElemType e , ElemType& next_e)
{
	return OK ;
}

template<typename ElemType>
SqList<ElemType> SqList<ElemType>::operator=(SqList<ElemType> rightL)
{
	
	if(this != &rightL)
	{
		if(listSize < rightL.listSize)
		{
			delete[] elem ;
			elem = new ElemType[rightL.listSize];
			
			listSize = rightL.listSize ;
		}
		n = rightL.n;
		for(int i = 1 ; i <= n ; ++i)
		{
			elem[i-1] = rightL.elem[i-1] ;
		}
	}
	return *this ;
}

template <typename ElemType>
Status SqList<ElemType>::priorElem(ElemType e , ElemType & prior_e)
{
	return OK;
}

template <typename ElemType>
int SqList<ElemType>::sequentialSearch(ElemType key)
{
	int i ; 
	for( i = 1 ; i <= n&& key!=elem[i-1] ; ++i);

	if(i <= n)
		return i ; 
	return 0 ;
}

template <typename ElemType>
SqList<ElemType>::SqList()
{
	elem = new ElemType[LIST_MAX_SIZE];
	listSize =  LIST_MAX_SIZE ;
	n = 0 ;
}

template <typename ElemType>
SqList<ElemType>::~SqList()
{
	delete[] elem ;
	n = 0 ;
}

template <typename ElemType>
SqList<ElemType>::SqList(const SqList<ElemType>& otherL)
{
	elem = new ElemType[otherL.listSize];
	listSize = otherL.listSize ;
	n = otherL.n ; 
	for(int i = 0 ; i <= n ; i++)
	{
		elem[i-1] = otherL.elem[i-1];
	}
}

