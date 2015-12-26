var redis = require('redis');

var client = redis.createClient(6379 , '127.0.0.1');
// 身份验证
client.auth('150700', function(err1){
	//console.log('auth');
	if(err1){
		console.log(err1);
	}else{
		//console.log('err event');
		client.on('error', function(err){
			console.log('Error '+ err);
		});
		// 字符串
		//setValue('color','red');
		//getValue('color');
		// 哈希值
		//var map1 = {'shelter':'c-person ten' ,'cooking':'campstove'};
		//setMapValue('camping',map1);
		//getMapValue('camping','cooking'); 

		// 链表
		//setListValue('tasks','1');
		//setListValue('tasks','2');
		//setListValue('tasks','3');
		//getListValue('tasks');

		// 集合操作
		//setSetValue('ip_adde','127.0.0.1');
		//setSetValue('ip_adde','127.0.0.1');
		//setSetValue('ip_adde','127.0.0.2');
		//getSetValue('ip_adde');


	}
});


function setValue(key,value){
	client.set(key,value,redis.print);
}

function getValue(key){
	client.get(key , function(err , value){
		if(err)
		{
			console.log(err);
		}
		else
		{
			console.log(key + '->' + value);
		}
	});
}

function setMapValue(key, value)
{
	client.hmset(key,value,redis.print);
}


function getMapValue(key,key1 ,value)
{
	client.hget(key ,key1, function(err , value){
		if(err)
		{
			console.log(err);
		}
		else
		{
			console.log(key +  '->'+ key1 + '->' + value);
		}
	});
}

function setListValue(key , value){
	client.lpush(key,value , redis.print);
}

function getListValue(key){
	client.lrange(key,0,-1 ,function(err , items){
		if(err){
			console.log(err);
		} else {
			items.forEach(function(item , i){
				console.log(" "+ item);
			});
		}
	});
}


function setSetValue(key,value){
	client.sadd(key , value,redis.print);
}

function getSetValue(key){
	client.smembers(key,function(err ,members){
		if(err){
			console.log(err);
		} else
		{
			console.log(members);
		}
	});
}



