package main 

import "io"
import "log"
import "net/http"
// http://192.168.1.101:8081/hello
func helloHandler(w http.ResponseWriter , r *http.Request) {
	io.WriteString(w , "Hello World")
}

func main() {
	http.HandleFunc("/hello" , helloHandler)
	err := http.ListenAndServe(":8081" ,nil)
	if err != nil {
		log.Fatal("ListenAndServe :" , err.Error())
	}
}