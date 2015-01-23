package main
import "fmt"

func adder() func(int) int {
	sum := 0
	return func(x int) int {
		sum += x
		return sum
	}
}
func test(){
	var pow = []int{1,2,3,4,5,6,7}
	for i , v := range pow {
		fmt.Printf("2**%d == %d\n",i,v)
	}
}
func main(){
	pos , neg := adder() , adder()
	fmt.Println(pos(2))
	fmt.Println(neg(-2))
	test()
}
