/**
发布预订实例
*/

var net = require('net');
var redis = require('redis');

var server = net.createServer(function(socket){
	var subscriber ; 
	var publisher ; 

	socket.on('connect' , function(){
		console.log('connect...');
		subscriber = redis.createClient();  // 订阅
		subscriber.subscriber('main_chat_room');

		subscriber.on('message',function(channel , message){
			socket.write('channel ' + channel + ":" + message);
		});
		
	});
	publisher = redis.createClient();  // 发布
	socket.on('data', function(data){
		console.log('data:',data);
		publisher.publish('main_chat_room','data');
	});

	socket.on('end', function(){
		subscriber.unsubscribe('main_chat_room');
		subscriber.end();
		publisher.end();
	});

});

server.listen(3000);