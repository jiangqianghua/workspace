package main

import "fmt"

type Integer int 

// 为 Integer增加 Less方法
func (a Integer) Less(b Integer) bool {
	return a < b
}
// 为Integer增加 ADD方法，这个时候添加的值a不会被增加，因为和C一样，go也是只传递
func (a Integer) Add1(b Integer){
	a += b
}
// 为Integer增加 ADD方法，这个时候添加的值a会被增加，go也可以传递指针
func (a *Integer) Add2(b Integer){
	*a += b
}
func main() {

	var a Integer = 3 
	if a.Less(4) {
		fmt.Println(a, "less 2")
	}

	fmt.Println(a);   // 3
	a.Add1(2)
	fmt.Println(a);   //3

	a.Add2(2)
	fmt.Println(a);   //5
}