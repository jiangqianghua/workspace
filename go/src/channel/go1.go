package main
import "fmt"

func sum(a []int , c chan int){
	sum := 0
	for _, v:= range a{

		sum += v
	}
	c <- sum
}

func fibonacci(n int , c chan int){
	x , y := 0 ,1
	for i := 0 ; i < n ; i++ {
		c <- x
		x , y = y , x + y
		close(c)
	}
//	close(c)
}
func main(){
	a := []int {7,2,8,-9,4,0}
	c := make(chan int)
	go sum(a[:len(a)/2],c)
	go sum(a[len(a)/2:],c)
	x , y := <-c , <-c

	fmt.Println(x , y , x + y)
//	fmt.Println(<-c)
	c1 := make(chan int ,2)
	c1 <- 1
	c1 <- 2
	fmt.Println(<-c1,<-c1)
	
	c2 := make(chan int, 10)
	go fibonacci(cap(c2),c2)
//	v1 ok := <-c2 
//	if ok == false {
//		fmt.Println("close c2")	
//	}
	for i := range c2 {
		fmt.Println(i)
	}
}
