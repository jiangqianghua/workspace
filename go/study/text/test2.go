package main 

import "fmt"
import "encoding/xml"
/*
type person struct {
	Name string
	Age int
}
*/
type person struct {
	Name string `xml:"name,attr"`   // 把name作为属性写入xml文件中
	Age int `xml:"age"`				// 以age写入xml标记中
}

func main(){
	p := person{Name:"jiang" , Age:18};

	// if data , err := xml.Marshal(p);err!=nil {
	// 	fmt.Println(err);
	// 	return ;
	// }else{
	// 	fmt.Println(string(data));
	// }

	// 结构体转字节（xml形式存放得字节）
	var data []byte  
	var err error
	if data , err = xml.MarshalIndent(p,""," ");err!=nil {
		fmt.Println(err)
		return 
	}
	fmt.Println(string(data));

	//字节转结构体
	p2:=new(person)

	if err = xml.Unmarshal(data , p2); err != nil{
		fmt.Println(err)
		return 
	}

	fmt.Println(p2)
/**
<person>
 <Name>jiang</Name>
 <Age>18</Age>
</person>
&{jiang 18}
*/
}