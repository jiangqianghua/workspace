// 普通中间件的使用
var connect = require('connect');
var app = connect();
app.use(logger);
app.use('/admin',restrict);
app.use('/admin',admin); //http://192.168.1.101:3000/admin/jiang3   url根必须是admin才会执行admin
app.use(hello);
app.listen(3000);

// 中间件 ，打印日志
function logger(req,res,next) {
	console.log('%s,%s',req.method , req.url);
	next();
}


function hello(req, res) {
	res.setHeader('Content-Type','text/plain');
	res.end('Hello world');
}
// 管理员验证
function restrict(req , res , next){
	var authorization = req.headers.authorization;
	if(!authorization) return next(new Error('Unauthorization'));
	var parts = authorization.split(' ');
	var scheme = parts[0];
	var auth = new Buffer(parts[1],'base64').toString().split(':');
	var user = auth[0];
	var pass = auth[1];

	if( user == 'root' && pass == '1234'){
		next();
	}else{
		next(new Error('cannot find admin user'));
	}
}

function admin(req,res,next){
	switch(req.url){
		case '/':    // admin自动被去掉了
			res.end('try /users');
			break;
		case '/users':
			res.setHeader('Content-Type','application/json');
			res.end(JSON.stringify(['tobi','loki','jane']));
			break;
	}
	console.log('admin connect');
	next();
}

// curl --user root:1234 http://192.168.1.101:3000/admin/users 
//["tobi","loki","jane"]
//curl http://192.168.1.101:3000/
//hello world
//curl --user root:1234 http://192.168.1dmin/
//try /users


