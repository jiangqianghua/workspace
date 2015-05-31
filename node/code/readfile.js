var fs = require("fs");
fs.readFile('./mymodule.js',function (err,data){
	if(err) throw err ;
	console.log('successfully');
});

console.log('async')
