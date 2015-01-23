package main
import "fmt"
import "runtime"
import "time"
func test(){
	fmt.Print("Go runs on")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("linux.")
	default:
		fmt.Println("%s.",os)
	}	
}
func test1(){
	fmt.Println("When's Saturday?")
	today := time.Now().Weekday()
	switch time.Saturday{
	case today+0:
		fmt.Println("Today.")
	case today+1:
		fmt.Println("Tomrrow.")
	case today+2:
		fmt.Println("In two days.")
	default:
		fmt.Println("Too far away.")
	}

}
func test2(){
	t := time.Now()
	switch{
	case t.Hour()<12:
		fmt.Println("Good morning!")
	case t.Hour()<17:
		fmt.Println("Good afternoom.")
	default:
		fmt.Println("Good evening")
	}
}
func main(){
	pow := make([]int,10)
	for i := range pow{
		pow[i] = 1<<uint(i)
	}
	for _, value := range pow{
		fmt.Printf("%d\n",value)
	}
	test()
	test1()
	test2()
}
