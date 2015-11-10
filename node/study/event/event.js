var EventEmitter = require('events').EventEmitter ;

var life = new EventEmitter();
life.setMaxListeners(5);  // 设置最大监听个数 ,最大5个，指的是同一事件
// addEventListener
life.on('求安慰',function(who){
	console.log('给' + who + ' 倒水');
});

life.on('求安慰',function(who){
	console.log('给' + who + ' 倒水1');
});

life.on('求安慰',function(who){
	console.log('给' + who + ' 倒水2');
});

life.on('求安慰',function(who){
	console.log('给' + who + ' 倒水3');
});

life.on('求安慰',function(who){
	console.log('给' + who + ' 倒水4');
});

function jiaogongzi(who){
	console.log('给' + who + ' 交工资2');
}

life.on('求溺爱',function(who){
	console.log('给' + who + ' 交工资');
});

life.on('求溺爱',jiaogongzi);


// 移除某一个函数方法，主意这个时候被监听的对应事件的函数不能是匿名的，否则无法移除
life.removeListener('求溺爱',jiaogongzi);  // 只能移除某个监听事件的某个对应函数
// 返回值表示是否有注册该时间
var hashConfortListener = life.emit('求安慰' ,'汉子');
var hashloveListener =  life.emit('求溺爱' ,'妹子');
var hashplayListener =  life.emit('求玩坏' ,'妹子');

// console.log(hashplayListener);
// console.log(hashloveListener);
// console.log(hashConfortListener);
// 批量移除事件
//life.removeAllListeners();
//移除具体事件所有函数
life.removeAllListeners('求安慰');

// 获取监听事件个数
console.log(life.listeners('求安慰').length);  //0
console.log(life.listeners('求溺爱').length);  //1
//console.log(EventEmitter.listenerCount(life,'求安慰')); // 5




