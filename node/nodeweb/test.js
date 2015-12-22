var http = require('http')
var url = require('url')
var items = [] ;

var server = http.createServer(function(req,res){
	switch(req.method){
		case 'POST':
			var item = '';
			console.log('post');
			req.setEncoding('utf-8');
			req.on('data',function(chunk){
				console.log('data' + chunk);
				item +=chunk ;
			});
			req.on('end',function(){
				console.log('end ');
				items.push(item);
				res.end('OK\n');
			});
			break;
		case 'GET':
			/**
			console.log('get ');
			items.forEach(function(item,i){
				res.write(i+') '+ item+'\n' );
			});
			res.end();
			*/
			// 优化处理
			var body = items.map(function(item,i){
				return i+')' + item ;
			}).join('\n');
			res.setHeader('Content-Length',Buffer.byteLength(body));
			res.setHeader('Content-Type','text/plain; charset="utf-8"');
			res.end(body);
			break;
		case 'DELETE':
			console.log("delete");
			var path = url.parse(req.url).pathname ;
			var i = parseInt(path.slice(1),10);
			console.log("path " + i);
			if(isNaN(i)){
				res.statusCode = 400 ; 
				res.end('Invalid item id');
			} else if(!items[i]) {
				res.statusCode = 404 ;
				res.end('item not found');
			}else {
				items.splice(i ,1);
				res.end('OK\n');
			}
			break;
	}
});

server.listen(3000);