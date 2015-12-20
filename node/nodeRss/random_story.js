var fs = require('fs')
var request = require('request')  // 需要安装对应的node包npm install request ， 否则程序无法找到request
var htmlparser = require('htmlparser') // 同上
var configFilename = './rss_feeds.txt'
// nodejs 串行程序编写方式
// 读取rss源
//1 检测rss_feeds.txt 是否存在
function checkForRSSFile(){
	fs.exists(configFilename,function(exists){
		if  (!exists){
			console.log("Missing RSS file:"+configFilename);
			//returtn next(new Error("Missing RSS file:"+configFilename));
		}
		next(null , configFilename);
	});
}

//2. 读取rss_feeds.txt文件
//3. 从rss_feeds.txt文件中随机选择一个rss 的url
function readRSSFile(configFilename){
	fs.readFile(configFilename , function(err , feedList){
		if (err) return next(err);

		feedList = feedList.toString().replace(/^\s+|\s+$/g,'').split("\n");
		var random = Math.floor(Math.random()*feedList.length);
		next(null , feedList[random]);
	});
}
//4. 开始下载rss源文件
function downloadRSSFeed(feedUrl){
	request({uri:feedUrl} , function(err , res , body){
		if (err) return next(err);
		if (res.statusCode != 200){
			return next(new Error('Abnormal response status code'));
		}
		next(null , body);
	});
}
//4 解析rss源
function parseRSSFeed(rss){
	//console.log(rss);
	var handler = new htmlparser.RssHandler();
	var parse = new htmlparser.Parser(handler);
	parse.parseComplete(rss);
	console.log(handler.dom.items);
	
	if(!handler.dom.items.length)
		return next(new Error('no rss items found'));

	//console.log("parseRSSFeed" + handler.dom.itmes); // 这个
	/**
	for (item in items)
	{
		console.log(item.title);
		console.log(item.link);
	}*/
	// 目前想办法解析出items
//	console.log(type(handler.dom.itmes));
//	var item = handler.dom.itmes.shift();

	//console.log(item.title);
	//console.log(item.link);
}
// 把上面5个流程方放入一个数组中，可以让其执行串行任务
var task = [checkForRSSFile , readRSSFile , downloadRSSFeed , parseRSSFeed];

// 执行下一个任务计划
function next(err , result){
	if(err) throw err ; 
	var currentTask = task.shift();  // 去除一个任务执行
	if(currentTask){
		currentTask(result);
	}
}

// 程序运行，开始执行
next();
