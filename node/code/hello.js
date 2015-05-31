var http = require("http");
var mymodule = require("./mymodule.js");
http.createServer(function (req , res)
{
	res.writeHead(200,{'Content-Type':'text/pain'});
	res.end('hello word\n');
}).listen(1337,"192.168.202.131");
console.log("Server running at http://192.168.202.131:1337/");

mymodule.showLog();
console.log(mymodule.location);
