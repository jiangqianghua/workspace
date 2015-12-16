package main 

//import "bufio"
import "fmt"
//import "os"
//import "strconv"
//import "strings"
import "mlib"
//import "mp"

var lib *mlib.MusicManager 
var id int = 1 
var ctrl , signal chan int

func main(){
	fmt.Println("test...");
	fmt.Println("Enter following commands to control the player:")
	fmt.Println("lib list --- view the existing music lib")
	fmt.Println("lib add <name><artist><source><type> -- Add a music to the music lib ")
	fmt.Println("lib remove <name> -- Remove the specified music from the lib ")
	fmt.Println("play <name> -- Play the specified music")

	lib = mlib.NewMusicManager()
}
