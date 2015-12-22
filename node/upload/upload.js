var http = require('http');
var formidable = require('formidable');
// http://192.168.1.101:3000
var server = http.createServer(function(req,res){
	switch(req.method){
		case 'GET':
			show(req, res);
			break;
		case 'POST':
			upload(req, res);
			break;
	}
});

function show(req , res){
	var html = ''
	+ '<form method="post" action="/" enctype="multipart/form-data">'
	+ '<p><input type="text" name="name" /></p>'
	+ '<p><input type="file" name="file"/></p>'
	+ '<p><input type="submit" value="Upload"/></p>'
	+ '</form>';
	res.setHeader('Content-Type','text/html');
	res.setHeader('Content-Length',Buffer.byteLength(html));
	res.end(html);
}

function upload(req , res){
	if(!isFormData(req)){
		res.statusCode = 404 ;
		res.end('Bad request:expecting multipart/form-data');
		return ;
	}

	var form = new formidable.IncomingForm();
	form.parse(req , function(err , fields , files){
		console.log(fields);
		console.log(files);
		res.end('upload complete!');
	});

	// 进度
	form.on('progress',function(bytesReceived , bytesExpected){
		var percent = Math.floor(bytesReceived/bytesExpected *100);
		console.log(percent);
	});

}

function isFormData(req) {
	var type = req.headers['content-type'] || '';
	return 0 == type.indexOf('multipart/form-data');
}

server.listen(3000);