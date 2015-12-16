package mp 

import "fmt"
import "time"
type MP3Player struct {
	stat int 
	progress int
}

func (p *MP3Player) Play(source string){
	fmt.Println("Play MP3 music" , source)

	p.progress = 0 

	for p.progress < 100 {
		time.Sleep(100 * time.Millisecond) // 模拟播放
		fmt.Println(".")
		p.progress += 10 
	}

	fmt.Println("\n Finished playing", source)
}
