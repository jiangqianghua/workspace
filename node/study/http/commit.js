
var http = require('http');
var querystring = require('querystring');

var postData = querystring.stringify({
	'content':'我喜欢imooc网络',
	'cid':348
});

var options = {
	hostname:'www.imooc.com',
	port:80,
	path:'/course/docomment',
	method:'POST',
	headers:{
		'Accept':'application/json, text/javascript, */*; q=0.01',
		'Accept-Encoding':'gzip,deflate',
		'Accept-Language':'zh-CN,zh;q=0.8',
		'Connection':'keep-alive',
		'Content-Length':postData.length,
		'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8',
		'Cookie':'imooc_uuid=af23fe1c-f5a6-4fea-b6d0-d63b6a6b4424; loginstate=1; apsid=NkMTQxNTFkMTkwYTQ5YWU5ZjZlYmU3OWFmZTA0NmYAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMTE5NTQ1NwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAyNDA0MzczMzlAcXEuY29tAAAAAAAAAAAAAAAAAAAAADliNWY1NzVhYWQwZDcwNGRiMjNhYmQyOWNhNzhmMDQ3pmm8VKZpvFQ%3DNW; PHPSESSID=rnrkbrj863ud8c948rfjvdrho3; cvde=560787ce8df2b-44; Hm_lvt_f0cfcccd7b1393990c78efdeebff3968=1443184814,1443281477,1443317129,1443333908; Hm_lpvt_f0cfcccd7b1393990c78efdeebff3968=1443356526',
		'Host':'www.imooc.com',
		'Origin':'http://www.imooc.com',
		'Referer':'http://www.imooc.com/course/comment/id/348',
		'User-Agent':'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.0 Safari/537.36',
		'X-DevTools-Emulate-Network-Conditions-Client-Id':'84F20ACE-B0D0-4318-955E-01AAFD4D202B',
		'X-Requested-With':'XMLHttpRequest'
	}
}

var req = http.request(options , function(res){
	console.log('status:'+res.statusCode);
	console.log('headers:'+JSON.stringify(res.headers));
	res.on('data',function(chunk){
		console.log(Buffer.isBuffer(chunk));
		console.log(typeof chunk);
	});

	res.on('end',function(){
		console.log('commit ok');
	});

});

req.on('error',function(e){
		console.log('Error:'+e.message);
});

req.write(postData);

req.end();