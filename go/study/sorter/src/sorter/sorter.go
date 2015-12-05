package main

import "flag"
import "fmt"
import "bufio"
import "io"
import "os"
import "strconv"

var infile *string = flag.String("i","infile","File contains values for sorting")
var outfile *string = flag.String("o","outfile","File to receive sorted values")
var algorithms *string = flag.String("a","qsort","Sort algorithms")

// read file
func readValues(infile string)(values []int , err error){
	file , err := os.Open(infile)
	if err != nil {
		fmt.Println("Failed to open the input file ", infile)
		return 
	}

	defer file.Close()

	br := bufio.NewReader(file)

	values = make([]int , 0)

	for {
		line , isPrefix , err1 := br.ReadLine()

		if err1 != nil {
			if err1 != io.EOF {
				err = err1 
			}
			break
		}

		if isPrefix {
			fmt.Println("A too line , seems unexpected.")
			return 
		}

		str := string(line) 
		value , err1 := strconv.Atoi(str)

		if err1 != nil {
			err = err1 
			return 
		}

		values = append(values , value)
	}

	return 
}

// write file
func writeValues(values []int , outfile string) error {
	file , err := os.Create(outfile)
	if err != nil {
		fmt.Println("Failed to create the output file ", outfile)
		return err 
	}

	defer file.Close()

	for _, value := range values{
		str := strconv.Itoa(value)
		file.WriteString(str+"\n")
	}
	return nil ;
}


func main(){
	flag.Parse()

	if infile != nil {
		fmt.Println("infile =",*infile , "outfile =",*outfile , "algorithms =",*algorithms)
	}

	// read file 
	values , err := readValues(*infile)

	if err == nil{
		fmt.Println("read values : ",values)
	}else{
		fmt.Println(err)
	}

	// write file
	err1 := writeValues(values , *outfile) 
	if err1 != nil {
		fmt.Println(err1)
	}
}