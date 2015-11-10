var module1 = require('./module1');
module1.name('jiang');
//var sum = rocker.add(1,2);  // 调用失败，没有设置export
var sum = module1.add1(1,2);  // add1 可以调用成功
console.log(sum);

module1.showlog();

var module2 = require("./module2"); // module2是函数的一个对象
var sum1 = module2(1,2);
console.log(sum1);
