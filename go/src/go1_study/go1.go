package main
import "fmt"

type Vertex struct{
	X int
	Y int
}
var m map[string] Vertex
func maptest(){
	m = make(map[string]Vertex)
	m["jiang"] = Vertex{
		1,2,
	}
	m["jiang1"] = Vertex{
		3,4,
	}
	fmt.Println(m["jiang"],m["jiang1"])
}
func maptest1(){
	var m = map[string]Vertex{
		"jiang":Vertex{
			1,2,	
		},
		"jiang1":Vertex{
			3,4,
		},
	}

	fmt.Println(m)
}

func maptest2(){
	m := make(map[string]int)
	m["Answer"] = 42
	fmt.Println("the value:",m["Answer"])
	delete(m,"Answer")
	fmt.Println("the value:",m["Answer"])
	v , ok := m["Answer"]
	fmt.Println("the value :",v , "Present?",ok)
}
func main(){
//	fmt.Println(Vertex{1,2})
//	v := Vertex{1,2}
//	v.X = 4
//	fmt.Println(v.X)

//	p := Vertex{1,2}
//	q := &p
//	q.X = 1e9
//	fmt.Println(p)
	
//	v := new(Vertex)
//	fmt.Println(v)
//	v.X, v.Y = 11 , 9
//	fmt.Println(v)
	maptest2()
}
