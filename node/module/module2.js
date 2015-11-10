var add = function(a , b){
	return a + b ;
}
/** 改写法错误，我们可以使用module改写*/
//exports = add ;
/** 使用module改写，可以正确执行*/
module.exports = add ;