package main 

//import "io"
import "fmt"
import "bufio"
import "strings"
import "os"
// 文件缓存
// 文件输出
func fileOut(){
	w := bufio.NewWriter(os.Stdout)  // 写出到屏幕

	fmt.Fprint(w , "hello")
	fmt.Fprint(w , " world\n")
	w.Flush() 
}

func main(){
	strReader := strings.NewReader("hello worlds")

	// 缓存字符
	bufReader := bufio.NewReader(strReader)

	data , _ := bufReader.Peek(5)  // 只读不取 。缓存数量不变

	fmt.Println(data , bufReader.Buffered())  // 打印缓冲了多少字符
	// [104 101 108 108 111] 12
	str , _ := bufReader.ReadString(' ')  
	fmt.Println(str , bufReader.Buffered()) // print hello 6

	fileOut()
 }