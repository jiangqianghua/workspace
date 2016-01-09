// 自带的connect中间件
var  connect = require("connect");
var query = require("query-string");
var app = connect() ; 
app.use(query.parse());
app.use(function(req,res){
	res.setHeader("Content-Type",'application/json');
	console.log(JSON.stringify(req.query));
	res.end(JSON.stringify(req.query));
});

app.listen(3000);