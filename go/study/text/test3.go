package main

import "fmt"
//import "os"
import "flag"
//  从命令行接受复杂得参数
func style1(){
	methodPtr := flag.String("method" , "default" , "method of sample");
	valuePtr := flag.Int("value",-1 ,"value of sample");

	flag.Parse();

	fmt.Println(*methodPtr , *valuePtr);


	//>> go build test3.go
	//>>./test3 -method jiang -value 20
}

func style2(){
	var method string ; 
	var value int ; 

	flag.StringVar(&method ,"method", "defalut" , "method of sample");
	flag.IntVar(&value ,"value", -1 , "value of sample");

	flag.Parse();

	fmt.Println(method , value);

		//>> go build test3.go
	//>>./test3 -method jiang -value 20
}

func main(){

	//style1();
	style2();

}