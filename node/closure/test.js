//详解node闭包的问题
// 获取函数名称，以确定是否是有名还是匿名函数
function getFunctionName(fun){
	if (fun.name !== undefined){
		return fun.name;
	}
	var ret = fun.toString();
	ret = ret.subStr('function'.length);
	ret = ret.subStr(0,ret.indexOf('('));
	return ret ;
}


// 有名函数
var fun1 = function fun1(){};
// 匿名函数
var fun2 = function(){};

console.log("fun1:"+getFunctionName(fun1));
console.log("fun2:"+getFunctionName(fun2));

//--------------------------------------------------------------
// 创建函数的几种方式
// 1 声明函数
function fn1(){}

// 2 创建匿名函数表达式
var fn2 = function(){};

// 3 创建具体函数表达式
var fn3 = function fn3Name(){
	console.log("in : fn3 <" ,typeof fn3 ,"> fnName <",typeof fn3Name , ">");
};  // 注意具体函数表达式函数名只能在创建内部使用

// 测试第三种方式
console.log("out : fn3 <" ,typeof fn3 ,"> fnName <",typeof fn3Name , ">");
fn3();
// print 

/**
fun1:fun1
fun2:
out : fn3 < function > fnName < undefined >
in : fn3 < function > fnName < function >
*/

// 自执行函数
(function(){console.log("自执行函数1")})();
(function fn4(){console.log("自执行函数2")})();


// 对象内定义函数
var o = {fn5:function(){console.log("对象内定义函数fn5")}};
o.fn5();
console.log("fun5:"+getFunctionName(o.fn5));  // 也属于匿名函数


// 测试
function fun(n,o) {
	console.log('n='+n+' o='+o);
	return {
		fun:function(m){
			console.log('m='+m);
			return fun(m,n);
		}
	}
}
var a = fun(0);
console.log(a);  
// print 
/**
n=0 o=undefined
{ fun: [Function] }
*/
console.log(a.fun(1));

// print 
/**
m=1
n=1 o=0
{ fun: [Function] }
*/

console.log(a.fun(2));

// print 
/**
m=2
n=2 o=0
{ fun: [Function] }
*/
console.log(a.fun(3));

// print 
/**
m=3
n=3 o=0
{ fun: [Function] }
*/

console.log('--------------------')
var b = fun(0).fun(1).fun(2).fun(3);
console.log(b)
// print 
/**
n=0 o=undefined
m=1
n=1 o=0
m=2
n=2 o=1
m=3
n=3 o=2
{ fun: [Function] }
*/

console.log('-----------------0');
var c = fun(0).fun(1);
console.log(c)

c.fun(2);
c.fun(3);

// print 
/**
m=2
n=2 o=1
m=3
n=3 o=1
*/







