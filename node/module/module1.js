/** 外部直接可以调用name方法*/
exports.name = function(user){
	console.log("My name is " + user );
}
/** 该函数是局部变量，外部无法调用*/
var add = function add( a ,  b){
	return a + b ;
}
/** add1 可以被外部调用到*/
exports.add1 = add ;

/** 该方法是全局变量，也可以被外部调用到*/
this.showlog = function(){
	console.log("this is log") ;
}

