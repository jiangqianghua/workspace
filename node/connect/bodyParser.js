// 自带的connect中间件
var  connect = require("connect");
// npm install body-parser
var bodyParser = require("body-parser");

var app = connect();
// parse application/x-www-form-urlencoded  
app.use(bodyParser.urlencoded({ extended: false }))  
// parse application/json  
app.use(bodyParser.json())  
app.use(function(req,res){
	res.end('Registered new user :'+req.body.username);
});
app.listen(3000);

//curl -d '{"username":"jiang"}' -H "Content-Type:application/json" http://192.168.1.103:3000
//curl -d username=jiang http://192.168.1.103:3000