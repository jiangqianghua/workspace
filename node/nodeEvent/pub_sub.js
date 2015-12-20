// telnet 192.168.1.101 888
var events = require('events')
var net = require('net')
var channel = new events.EventEmitter();
channel.clients = {};
channel.subscriptions = {}

channel.on('join',function(id , client){
	this.clients[id] = client 
	this.subscriptions[id] = function(senderId , message) {
		if (id != senderId) {
			this.clients[id].write(message)
		}
	}
	this.on('broadcast' , this.subscriptions[id])
	var welcome = "welcome!\n Guests online:" + this.listeners('broadcast').length ;
	client.write(welcome+"\n");
});

channel.on('leave' , function(id){
	channel.removeListener('broadcast',this.subscriptions[id]);
	channel.emit('broadcast',id , '\n'+id+'has left the chat\n');
});

channel.on('shutdown',function(){
	channel.emit("broadcast",'',"chat has shut down");
	channel.removeAllListeners('broadcast');
}); 

channel.setMaxListeners(50)

var server = net.createServer(function(client){
	var id = client.remoteAddress + ':' + client.remotePort ;
	channel.emit('join',id , client);
	client.on('data',function(data){
		data = data.toString();
		if(data == "shutdown\r\n"){
			channel.emit("shutdown");
		}
		channel.emit('broadcast',id,data)
	});

	client.on('close' , function(){
		channel.emit('leave',id);
	});
});

server.listen(8888)