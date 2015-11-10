var pet = {
	words:'...',
	speak: function(){
		console.log(this.words);
		console.log(this);
		console.log(this == pet); // true this指向当前的对象
	}
};

pet.speak();
console.log('-------------------');

function pet1(words){
	this.words = words ;
	console.log(this.words);
	console.log(this == pet1);  // false this指向顶层的global
	console.log(this == global)  //true

}

pet1('...')

function pet2(words){
	this.words = words;
	this.speak = function(){
		console.log(this.words);
		console.log(this == pet2); // false
		console.log(this == this.speak); // false
		console.log(this);  // print { words: 'miao', speak: [Function] }
	}
}
console.log('--------------------');
var cat = new pet2('miao');
cat.speak();