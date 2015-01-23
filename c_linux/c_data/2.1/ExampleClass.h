
template <typename ElemType>
class Rectangle
{
public:
	class RectangleNo
	{
		public:
			int no ;
	};
	// operator "="
	Rectangle operator = (Rectangle rightR);
	// set length
	void setLength(ElemType l) ;
	// set No
	void setNo(int i);
	//structor
	Rectangle();
	
	Rectangle(const Rectangle <ElemType> & otherD);
	
	virtual ~Rectangle() ;
protected:
	ElemType length ; 
	ElemType width ; 
	RectangleNo myNo ; 
};
