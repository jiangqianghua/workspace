//斗鱼tv直播 2015-12-20

// 使用nodejs并行技术实现单词计数功能

//描述 我们会用多个任务去计算单词的数量，然后最后合并，这样模拟堕多台服务器同时执行一个事情的，最后归总，这个在
// 很多分布式项目中会用到的技术，也是大数据统计一个高效的执行过程


var fs = require('fs')
var completedTasks = 0 ;  // 统计多少个任务已经完成
var tasks = [] ;  // 任务数组

var wordCounts = {} ; // 统计单词个数
var fileDir = './text' ; // 要统计单词的目录

//检测是否有完成的统计,没统计完一次，都会调用该函数
function checkIfComplete(){
	completedTasks++ ; // 统计数加1
	if(completedTasks == tasks.length){// 判断是否全部统计完
		for(var index in wordCounts)
			console.log(index+": " + wordCounts[index]);   // 打印多个统计结果
	}
}

// 计算多少个单词在文本中
function countWordsInText(text) {
	var words = text.toString().toLowerCase().split(' ').sort();  // 对text文本进行切割
	//console.log(words + " -- ");
	// 遍历获取到的单词
	for(var index in words) {
		var word = words[index] ;
		if(word)
		{
			// wordCounts是一个字典，存放了key是单词， value是单词对应的个数
			wordCounts[word] = (wordCounts[word]) ? wordCounts[word]+1 : 1; 
			//console.log(word+":"+wordCounts[word])
		}
	}
}


// 开始执行读取
fs.readdir(fileDir,function(err , files){
	if (err) throw err ;
	//console.log(files);
	// 遍历出每个需要统计的文本
	for(var index in files){
		var task = (function(file){  // 匿名函数
			//console.log(files[index]);  // ok
			return function(){  // 使用闭包
				//console.log(file);  // ok
				fs.readFile(file , function(err , text){ // 读取每个文本
					if (err) throw err ;
					countWordsInText(text) // 统计这个文本的单词个数
					checkIfComplete();   // 检测是否统计完
				});
			}
		})(fileDir+'/'+files[index]);
		tasks.push(task);
	}

	for(var task in tasks){
		tasks[task]();
	}
});

