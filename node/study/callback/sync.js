var c = 0 ;

function printIt() {
	console.log(c);
}
/**
function plus(){
	setTimeout(function(){
		c += 1;
	},1000)
	
}
**/
/**
	node是异步编程，必须需要用回调方式来执行需要同步的操作
*/
function plus(callback){
	setTimeout(function(){
		c += 1 ;
		callback();
	},1000)
}

plus(printIt);
//printIt();