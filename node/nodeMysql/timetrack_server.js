var http = require('http');
var work = require('./lib/timetrack');
var mysql = require('mysql')

var db = mysql.createConnection({
	host : '127.0.0.1',
	user : 'root',
	password : '150700',
	database : 'timetrack'
});


var server = http.createServer(function(req , res){

});

server.listen(3000);