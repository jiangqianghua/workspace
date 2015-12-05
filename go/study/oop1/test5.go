package main

import "fmt"

type Base struct{
	Name string 
}

func (base *Base) Foo(){
	fmt.Println("Foo")
}

func (base *Base) Bar(){
	fmt.Println("Bar")
}

type Foo struct {
	Base
}

func (foo *Foo) Bar(){
	foo.Base.Bar()
}

func main(){
	var foo *Foo = new (Foo)
	foo.Bar()  // print Bar
	foo.Foo() // print Foo
}