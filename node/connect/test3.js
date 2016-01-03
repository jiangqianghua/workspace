// 自带的connect中间件
var  connect = require("connect");
var cookieParser = require("cookie-parser");
var app = connect() ;
//npm install cookie-parser

app.use(cookieParser('jqh'));
app.use(function(req , res){
	console.log(req.cookies);
	console.log(req.signedCookies);
	res.end('hello\n');
});

app.listen(3000);