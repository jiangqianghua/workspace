package main 

import "encoding/binary"
import "fmt"
import "os"

type BitmapInfoHeader struct{

	Size uint32 
	Width int32
	Height int32
	Places uint16
	BitCount uint16
	Compression uint32
	SizeImage uint32
	XperlsPerMeter int32
	YperlsPerMeter int32
	ClsrUsed uint32
	ClrImportant uint32
}
func main(){

	file,_ := os.Open("image.bmp")

	defer file.Close()

	var headA , headB byte

	// 读取图片格式
	binary.Read(file , binary.LittleEndian , &headA)
	binary.Read(file , binary.LittleEndian , &headB)

	fmt.Printf("%c%c\n" , headA , headB)  // BM

	// 读取图片大小
	var size uint32 
	binary.Read(file , binary.LittleEndian , &size)
	fmt.Println("size=",size)  // BM

	// 保留变量
	var reservedA ,reservedB uint16
	binary.Read(file , binary.LittleEndian , &reservedA)
	binary.Read(file , binary.LittleEndian , &reservedB)
	fmt.Println("reserved : ",reservedA , reservedB)  // BM

	// 数据偏移文字，真正数据那一位开始
	var offbits uint32 
	binary.Read(file , binary.LittleEndian , &offbits)
	fmt.Println("offbit=",offbits)  // BM

	// 利用结构体统一读取	
	infoHeader := new(BitmapInfoHeader)
	binary.Read(file , binary.LittleEndian,infoHeader)

	fmt.Println(infoHeader)
}