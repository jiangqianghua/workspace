package main 

import "fmt"
import "strings"
import "strconv"

func main(){
	// string uses 

	s:= "hello world";
	// 包含
	fmt.Println(strings.Contains(s, "hello"), strings.Contains(s,"?"));
	//index
	fmt.Println(strings.Index(s,"o")) ; // index start by 0
	// splite
	ss:= "1#22#333#4444"
	spliteStr:= strings.Split(ss , "#");
	fmt.Println(spliteStr);
	// join
	fmt.Println(strings.Join(spliteStr,"#"));
	// prefix   suffix
	fmt.Println(strings.HasPrefix(s,"he"));
	fmt.Println(strings.HasSuffix(s,"ld"));

	//  字符串转换
	//转化
	fmt.Println(strconv.Itoa(10)); // int to string
	fmt.Println(strconv.Atoi("711")); // string to int
	//解析
	fmt.Println(strconv.ParseBool("false"));
	fmt.Println(strconv.ParseFloat("3.14",64));
	//格式化
	fmt.Println(strconv.FormatBool(true));
	fmt.Println(strconv.FormatInt(123,2)); //二进制
}