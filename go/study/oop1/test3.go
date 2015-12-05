package main

import "fmt"


type File struct{

}

func (f *File) Read(){
	fmt.Println("Read...")
}

func (f *File) Write(){
	fmt.Println("Write...")
}
type IFile interface {
	Read()
	Write()
}

type IReader interface{
	Read();
}

type IWrite interface{
	Write()
}

func main(){
	var file IFile = new (File)
	file.Write()
	file.Read()

	var file1 IReader = new (File)
	file1.Read()

	var file2 IWrite = new (File)
	file2.Write()
}