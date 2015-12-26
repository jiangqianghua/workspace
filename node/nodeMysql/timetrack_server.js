// 斗鱼tv  2015 12 26
// 编写一个日志系统的web应用程序
// 技术   nodejs   mysql  redis  mongoDb
// 功能   1 添加一天的日志  2 删除日志  3 修改日志  4 对日志归档  ....

var http = require('http');
var work = require('./lib/timetrack');
var mysql = require('mysql');
// mysql db 参数
var db = mysql.createConnection({
	host : '192.168.1.101',
	user : 'root',
	password : '150700',
	database : 'timetrack'
});

// 创建服务器对象
var server = http.createServer(function(req , res){
	console.log(req.method +" "+ req.url);
	switch (req.method) {
		case 'POST':
			switch (req.url){
				case '/':
					work.add(db,req,res);
					break;
				case './archive':
					work.archive(db , req, res);
					break;
				case '/delete':
					work.delete(db , req , res);
					break;
			}
			break;
		case 'GET':
			switch(req.url) {
				case '/':
					console.log("get show");
					work.show(db , res);
					break;
				case '/archived':
					work.showArchived(db ,res);
					break;
			}
			break;
	}
});

// 创建数据库，如果存在，不需要创建
db.query(
	"CREATE TABLE IF NOT EXISTS work ("
		+ "id INT(10) NOT NULL AUTO_INCREMENT ,"   // id  自动增长AUTO_INCREMENT
		+ "hours DECIMAL(5,2) DEFAULT 0 ,"     // 小时  
		+ "date DATE ,"   					   // 时间
		+ "archived INT(1) DEFAULT 0 ,"   	   // 归档 
		+ "description LONGTEXT ,"			   // 描述
		+ "PRIMARY KEY(id))" ,                  // 设置主键是key
		function(err) {
			// 创建数据库后执行的函数
			if (err) {
			//	throw err ; 
				console.log(err);
			}
			console.log('Server started...');
			// 开始执行程序，监听端口
			server.listen(3000);
		}
	);