package main 

import "fmt"
import "time"

// test timeout
// 利用select实现超时功能
func main(){
	timeout := make(chan bool , 1)
	ch := make(chan int , 1)
	go func(){
		time.Sleep(1e9) // wait 1 m
		timeout <- true ;
	}()

	select {
	case <- ch:
	case <- timeout:
	}

	fmt.Println("run time out")
}