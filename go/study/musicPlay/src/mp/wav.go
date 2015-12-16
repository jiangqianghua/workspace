package mp 

import "fmt"
import "time"
type WAVPlayer struct {
	stat int 
	progress int
}

func (p *WAVPlayer) Play(source string){
	fmt.Println("Play WAV music" , source)

	p.progress = 0 

	for p.progress < 100 {
		time.Sleep(100 * time.Millisecond) // 模拟播放
		fmt.Println(".")
		p.progress += 10 
	}

	fmt.Println("\n Finished playing", source)
}
