package main 
import "fmt"

type PersonInfo struct{
	ID string 
	Name string
	Address string 
}

func main() {
	var personDB map[string] PersonInfo
	personDB = make(map[string] PersonInfo)

	personDB["11"] = PersonInfo{"11","jiang","beijin"}
	personDB["22"] = PersonInfo{"22","huadan","shanghai"}

	person , ok := personDB["11"]

	if ok {
		fmt.Println("found person ", person.Name , "with id ",person.ID)	
	} else {
		fmt.Println("not found person with id 11")	
	}

	delete(personDB,"11")

	person1 , ok := personDB["11"]

	if ok {
		fmt.Println("found person ", person1.Name , "with id ",person1.ID)	
	} else {
		fmt.Println("not found person with id 11")	
	}

	//fmt.Println("hello") ;

}