// 文本监控器
var events = require("events")
var util = require("util")
var fs = require("fs")

function Watcher(watchDir , processedDir){
	this.watchDir = watchDir ;
	this.processedDir = processedDir ;
}

util.inherits(Watcher , events.EventEmitter);
//Watcher.prototype = new events.EventEmitter();
watchDir = './watch';
processedDir = './done';

Watcher.prototype.watch = function(){
	var watcher = this ;
	fs.readdir(this.watchDir , function(err , files){
		if (err){
			console.log(err)
			throw err ; 
		} 
		for(var index in files){
			console.log("file "+ files[index]);
			watcher.emit('process' , files[index]);
		}
	});
}

Watcher.prototype.start = function(){
	var watcher = this ; 
	fs.watchFile(watchDir , function(){
		console.log("watchFile ");
		watcher.watch();
	});
}

var watcher = new Watcher(watchDir,processedDir);

watcher.on('process' , function(file){
	var watchFile = this.watchDir + '/'+file ; 
	var processedFile = this.processedDir+ '/' + file.toLowerCase();
	console.log(watchFile + "  "+processedFile )
	fs.rename(watchFile,processedFile ,function(err){
		if (err) throw err ;
	});
});

watcher.start()
