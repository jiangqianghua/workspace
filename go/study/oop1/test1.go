package main

import "fmt"

func main(){

	var a = [3]int{1,2,3}

	var b = a 

	b[1]++

	fmt.Println(a,b)  // [ 1 2 3 ] [ 1 3 3]

	var c = &a

	c[1]++

	fmt.Println(a,*c) // [ 1 3 3 ] [ 1 3 3]
}