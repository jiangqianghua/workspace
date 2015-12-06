var momObj = function()
{

	this.x ; 
	this.y ;
	this.angle ;
	this.bigEye = new Image();
	this.bigEye0 = new Image();
	this.bigBody = new Image();
	this.bigTail = new Image();
	this.eyeIndex ; 
	this.delayeyeIndex ; 


	this.momTailCount = 0 ; 
	this.momTailTimer = 0 ;

	this.momBodyCount = 0 ;
}

momObj.prototype.init = function(){

	this.x = canWidth * 0.5 ;
	this.y =  canHeight * 0.5  ;
	this.angle = 0 ;
	this.bigEye.src = "./src/bigEye0.png";
	this.bigEye0.src = "./src/bigEye1.png";
	this.bigBody.src = "./src/bigSwim0.png";
	this.bigTail.src = "./src/bigTail0.png";

	this.eyeIndex = 0 ;
	this.delayeyeIndex = Math.random() * 500 ;

}

momObj.prototype.draw = function(){

	this.eyeIndex++ ;
	// 趋向跟随
	this.x = lerpDistance(mx , this.x , 0.95);
	this.y = lerpDistance(my , this.y , 0.95);
	// delta angle 计算角度
	var deltaY = my - this.y ; 
	var delatX = mx - this.x ; 
	var beta = Math.atan2(deltaY , delatX) + Math.PI; //【-pi ,pi】
	// lerp angle 
	this.angle = lerpAngle(beta , this.angle , 0.6) ;
	ctx1.save();
	ctx1.translate(this.x , this.y);
	ctx1.rotate(this.angle);

	var bobyImage ;
	if(data.double == 1) // ora
	{
		bobyImage = momBodyOra[this.momBodyCount];
	}
	else
	{
		bobyImage = momBodyBlue[this.momBodyCount];
	}
	ctx1.drawImage(bobyImage,-bobyImage.width * 0.5 , -bobyImage.height * 0.5);
	
	this.momTailTimer += deltaTime ;
	if(this.momTailTimer > 50)
	{
		this.momTailCount = (this.momTailCount + 1) % 8 ; 
		this.momTailTimer += 50 ;
	}
	var momTailCount = this.momTailCount ; 
	//console.log("momTailCount "+momTailCount);
	ctx1.drawImage(momTail[momTailCount],-momTail[momTailCount].width * 0.5 + 30, -momTail[momTailCount].height * 0.5);
	var eye = this.bigEye ;
	if(this.eyeIndex > this.delayeyeIndex && this.eyeIndex < (this.delayeyeIndex+10) )
		eye = this.bigEye0 ; 
	if(this.eyeIndex >= (this.delayeyeIndex+10))
	{ 
		this.delayeyeIndex = Math.random() * 500 ;
		this.eyeIndex = 0 ;
	}
	ctx1.drawImage(eye, -this.bigEye.width * 0.5 , -this.bigEye.height * 0.5);
	ctx1.restore();
}