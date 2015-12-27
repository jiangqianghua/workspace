// 可配置的中间件
var connect = require('connect');
var router = require('./middleware/router');



// 创建可以配置的日志系统
function logger(format){
	var regexp = /:(\w+)/g;

	return function(req , res , next) {
		var str = format.replace(regexp , function(match , property){
			return req[property];
		});
		console.log(str);
		next();
	}
}

module.exports = logger ;

var routes = {
	GET:{
		'/users':function(req,res){
			res.end('tobi , loki , ferret');
		},
		'/user/:id':function(req , res , id){
			res.end('user '+ id);
		}
	},
	DELETE:{
		'/user/:id': function(req, res, id){
			res.end('deleted user '+id);
		}
	}
};

function errorHandler(){
	var env = process.env.NODE_ENV || 'development';
	return function(err , req ,res,next){
		res.statusCode = 500 ;
		switch(env){
			case 'development':
				res.setHeader('Content-Type','application/json');
				res.end(JSON.stringify(err));
				break;
			default:
				res.end('Server error');
		}
	}
}

var app = connect();
app.use(logger(':method :url'));
//app.use(setup(':method'));
app.use(router(routes));
app.listen(3000);
