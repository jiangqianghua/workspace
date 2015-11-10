var pet = {
	words:'...',
	speak: function(say){
		console.log(say + ' '+ this.words)
	}
};

pet.speak('Speak'); // Speak ...


var dog = {
	words:'Wang'
};
console.log('-----------------------');
//利用call改变了this的上下文，把this指向的dog这个对象
// 这样方便实现继承
pet.speak.call(dog , 'speak'); //speak Wang