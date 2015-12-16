package main 

import "bufio"
import "fmt"
import "os"
import "strconv"
import "strings"
import "mlib"
import "mp"

var lib *mlib.MusicManager 
var id int = 1 
var ctrl , signal chan int

func handleLibCommands(tokens []string){
	switch tokens[1] {
	case "list":
		for i := 0 ; i < lib.Len(); i++ {
			e , _ := lib.Get(i)
			fmt.Println(i+1 ,":" , e.Name , e.Artist , e.Source , e.Type)
		}
		case "add": {
			if len(tokens) == 6 {
				id++ 
				lib.Add(&mlib.MusicEntry{strconv.Itoa(id),tokens[2],tokens[3] ,tokens[4] ,tokens[5]})
			}else {
				fmt.Println("Usage: lib add <name><artist><source><type>")
			}
		}
	case "remove":
		if len(tokens) == 3 {
			//lib.Remove(strconv.Atoi(tokens[2]))
		} else {
			fmt.Println("Uage: lib remove <index>")
		}
	default:
		fmt.Println("Unrecoginzed lib commabd:",tokens[1])
	}
}


func handlePlayCommand(tokens []string) {
	if len(tokens) != 2 {
		fmt.Println("Usage:play<name>")
		return 
	}

	e := lib.Find(tokens[1])
	if e == nil {
		fmt.Println("The music" , tokens[1] ,"does not exist")
		return 
	}

	mp.Play(e.Source , e.Type)
}


func main(){
	fmt.Println("test...");
	fmt.Println("Enter following commands to control the player:")
	fmt.Println("lib list --- view the existing music lib")
	fmt.Println("lib add <name><artist><source><type> -- Add a music to the music lib ")
	fmt.Println("lib remove <name> -- Remove the specified music from the lib ")
	fmt.Println("play <name> -- Play the specified music")

	lib = mlib.NewMusicManager()

	r := bufio.NewReader(os.Stdin)

	for{
		fmt.Println("Enter command ->")
		rawLine , _ , _ := r.ReadLine()
		line := string(rawLine)
		if line == "q" || line == "e" {
			break
		}
		tokens := strings.Split(line," ")

		if tokens[0] == "lib"{
			handleLibCommands(tokens)
		} else if tokens[0] == "play" {
			handlePlayCommand(tokens)
		} else {
			fmt.Println("Unrecoginzed command:",tokens[0])
		}
	}
}
