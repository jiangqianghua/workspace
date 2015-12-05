// 字符格式化
package main 

import "fmt"
import "os"

type Data struct{

}

func (self Data) String() string {
	return "data"
}
func main(){

	str := fmt.Sprintf("float %f",3.14) // 格式化字符串
	fmt.Println(str)
	fmt.Printf("%v\n",Data{})  // 自动调用结构的String方法

	fmt.Fprintln(os.Stdout,"A\n")  // 自动输出位置   os.Stdout 表示控制台输出

	
}