package main
import "fmt"
type Reader interface{
	Read() string ;
}

type Writer interface{
	Writer(str string) int 
}
type ReaderWriter interface{
	Reader
	Writer
}
func main(){
	var r Reader
//	var w Writer
	var rw ReaderWriter
	myFile :=new(MyFile)
	myFile.str = "jiangqianghua"
	fmt.Println(myFile.Read())
	r = myFile
	fmt.Println(r.Read())
	rw.Reader = r 
	fmt.Println(rw.Reader.Read())
}


type MyFile struct{
	str string
}

func (m *MyFile) Read() string{
	return "hello world"
}

func (m *MyFile) Write(str string) int{
	fmt.Println(str)
	return 1 
} 
