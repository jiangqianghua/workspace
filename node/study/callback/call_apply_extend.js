
function Pet(words){
	this.words = words ;
	this.speak = function(){
		console.log(this.words);
	}
}

function Dog(words){
	Pet.call(this,words); // 继承了Pet的属性和方法
}

var dog = new Dog('Wang');

dog.speak();