var fs = require('fs');
var path = require('path');
var args = process.argv.splice(2);
var command = args.shift();
//console.log(command);
var taskDescription = args.join(' ');
//console.log(taskDescription);
var file = path.join(process.cwd(),'./tasks');
//console.log(file);


switch (command) {
	case 'list':
		listTasks(file);
		break; 
	case 'add':
		addTask(file , taskDescription) ;
		break; 
	default:
		console.log('Usage: '+ process.argv[0] + ' list[add [taskDescription]]');
}

function loadOrInitializeTaskArray(file ,cb){
	fs.exists(file , function(exists) {
		var task = [] ;
		if(exists){
			fs.readFile(file , 'utf-8' , function(err , data){
				if(err) throw err ; 
				var data = data.toString();
				tasks = JSON.parse(data || '[]');
				//console.log("task:",tasks);
				cb(tasks)
			});
		} else {
			cb([]);
		}
	});
}

function listTasks(file){
	loadOrInitializeTaskArray(file , function(tasks){
		//console.log("task--- ",tasks);
		for(var i in tasks){
			console.log(tasks[i]);
		}
	});
}

function storeTasks(file , tasks){
	fs.writeFile(file , JSON.stringify(tasks),'utf-8',function(err){
		if(err) throw err ; 
		console.log('Saved.');
	});
}

function addTask(file , taskDescription){
	loadOrInitializeTaskArray(file , function(tasks){
		tasks.push(taskDescription);
		storeTasks(file , tasks);
	});
}