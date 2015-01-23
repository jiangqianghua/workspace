package main
import "fmt"

func test(){
	a := make([]int,5)
	ps("a",a)
	b := make([]int , 0 ,5)
	ps("b",b)
	c := b[:2]
	ps("c",c)
	d := c[2:5]
	ps("d",d)
}
func ps(s string , x []int){
	fmt.Printf("%s len=%d cap=%d %v\n",s , len(x) ,cap(x) , x)
}
func main(){
	p := []int{1,2,3,4,5,6}
	fmt.Println("p==",p)
	for i := 0 ; i < len(p) ; i++ {
		fmt.Printf("p[%d]==%d\n",i,p[i])
	}
	fmt.Println("p[1:3]==",p[1:3])
	fmt.Println("-------------")
	test()
}
