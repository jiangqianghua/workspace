package main 

import "fmt"


// 结构体

type Rect struct {
	x , y float64
	width , height float64
}

func (r *Rect) Area() float64{
	return r.width * r.height
}

func main(){
	//r := new (Rect)
	//r1 := &Rect{}
	//r2 := &Rect{0,0,100,100}
	r3 := &Rect{width:100,height:200}
	r3.width = 200
	fmt.Println(r3.Area())
}