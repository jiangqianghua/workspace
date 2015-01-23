package main
import "fmt"
import "math"

func add(x,y int) int {
	return x + y
}
func swap(x,y string)(string,string){
	return y,x
}
func split(sum int)(x,y int){
	x = sum * 4/9
	y = sum -x
	return 
}
func pow(x,n,lim float64) float64{
	if v := math.Pow(x,n) ; v < lim {
		return v
	}
	return lim
}
func main(){
	//	fmt.Println("Happy",math.Pi,"Day")
//	fmt.Printf("Now you have %g problems.",math.Nextafter(2,3))
//	fmt.Println(add(1,2))
//	a,b := swap("hello","world")
//	fmt.Println(a,b)
//	fmt.Println(split(17))
//	sum := 0
//	for i := 0 ; i < 10 ; i++ {
//		sum += i 
//	}
//	fmt.Println(sum)
	fmt.Println(
		pow(3,2,10),
		pow(3,3,20),
	)	
}
