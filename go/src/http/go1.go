package main
import(
	"fmt"
	"net/http"
)

func ServeHttp(w http.ResponseWriter , r *http.Request){
	fmt.Println("http request...")
	fmt.Fprint(w,"hello!")
}
func main(){
	http.HandleFunc("/hello",ServeHttp)
	err := http.ListenAndServe("localhost:4000",nil)
	if err != nil{
		fmt.Println("err")
	}
}

