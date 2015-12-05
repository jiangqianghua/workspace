package main

import "fmt"

type Integer int 

func (a Integer) Less(b Integer) bool{
	return a < b 
}

func (a *Integer) Add(b Integer){
	*a += b 
}

type LessAdder interface{
	Less(b Integer) bool 
	Add(b Integer)
}

func main(){
	var a Integer = 1
	flag := a.Less(3)
	if flag {
		fmt.Println(">")
	} else {
		fmt.Println("<")
	}

//	var b LessAdder = a  // is wrong
	var c LessAdder = &a 
	flag = c.Less(2)
	if flag {
		fmt.Println(">")
	} else {
		fmt.Println("<")
	}
}