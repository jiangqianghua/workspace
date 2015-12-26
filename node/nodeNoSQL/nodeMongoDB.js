var mongodb = require('mongodb');
var server = new mongodb.Server('127.0.0.1',1234,{});
var client = new mongodb.Db('mydatabase',server,{w:1});
client.open(function(err){
	if(err){
		console.log(err);
	} else {
		console.log('ok...');
	}
});
