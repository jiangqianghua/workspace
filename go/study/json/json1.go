package main

import "fmt"
import "encoding/json"

type Book struct {
	Title string 
	Authors []string 
	Publisher string
	IsPublished bool
	Price float32
}

func main(){
	authors := []string{"jiang","dan","qian"}
	book := &Book{"go 语言" ,nil ,"www.golang.com",true, 100.0}
	book.Authors = authors 
	fmt.Println(book)
	fmt.Println("json marshal ----")
	// json编码
	b , err := json.Marshal(book)
	if err != nil {
		fmt.Println("json marshal err :" , err)
	}
	fmt.Println(string(b))
	fmt.Println("json Unmarshal ----")
	// json 解码
	var book1 Book 
	err = json.Unmarshal(b , &book1)
	if err != nil {
		fmt.Println("json Unmarshal err :" , err)
	}
	fmt.Println(book1)

	//&{go 语言 [jiang dan qian] www.golang.com true 100}
	//json marshal ----
	//{"Title":"go 语言","Authors":["jiang","dan","qian"],"Publisher":"www.golang.com","IsPublished":true,"Price":100}
	//json Unmarshal ----
	//{go 语言 [jiang dan qian] www.golang.com true 100}

	fmt.Println("json Unmarshal unknow ----")

	// 把未知的json解码出来
	b = []byte(`{"Name":"jiangqianghua","age":28,"book":["go book","c++ boo"],"sex":true}`)
	var r interface{}
	err = json.Unmarshal(b,&r)
	if err != nil {
		fmt.Println("json Unmarshal err :" , err)
	}
	fmt.Println(r)

	person , ok := r.(map[string]interface{})
	if ok != true{
		fmt.Println("map err :" , ok)
	}
	fmt.Println(person)
	// 遍历map对象 person
	if ok {
		for k , v := range  person {
			switch v2 := v.(type) {
				case string:
					fmt.Println(k , "is string " , v2)
				case int:
					fmt.Println(k , "is int " , v2)
				case []interface{}:
					fmt.Println(k , "is array ")
					for i , iv := range v2 {
						fmt.Println(i , iv)
					}
				case bool:
					fmt.Println(k , "is bool " , v2)
				default:
					fmt.Println(k , "is another type not handle yet.")

			}
		}

	}


}