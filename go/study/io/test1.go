package main 

import "fmt"
import "io"
import "strings"
import "os"

func ReadFrom(reader io.Reader , num int)([]byte , error){
	p := make([]byte ,num)

	n , err := reader.Read(p)

	if n > 0 {
		return p[:n],nil 
	}

	return p ,err 
}

// 简单字符串输入
func sampleReadFromString(){
	data, _ := ReadFrom(strings.NewReader("from strng"),12)
	fmt.Println(data)
}
// 从文件读取
func sampleReadFile(){

	file ,_ := os.Open("test1.go")

	defer file.Close()

	data , _ := ReadFrom(file , 9)

	fmt.Println(data)
	fmt.Println(string(data))

}
// 从命令行读取
func sampleReadStdin(){
	fmt.Println("please input:")

	data , _ := ReadFrom(os.Stdin , 11)

	fmt.Println(data)
}
func main(){
	//sampleReadFromString()
	//sampleReadStdin()
	sampleReadFile()
}