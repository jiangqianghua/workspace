var babyObj = function()
{
	this.x ;
	this.y ; 
	this.angle ;
	this.babyEye = new Image();
	this.babyEye0 = new Image();
	this.babyBody = new Image();
	this.babyTail = new Image();
	this.eyeIndex ; 
	this.delayeyeIndex ; 

	this.babyBodyTimer = 0 ;
	this.babyBodyCount = 0 ;
}

babyObj.prototype.init = function()
{
	this.x = canWidth*0.5 - 50 ;
	this.y = canHeight*0.5 + 50 ; 
	this.angle = 0 ;
	this.babyEye.src = "./src/babyEye0.png";
	this.babyBody.src = "./src/babyFade0.png";
	this.babyTail.src = "./src/babyTail0.png";
	this.babyEye0.src = "./src/babyEye1.png";

	this.eyeIndex = 0 ;
	this.delayeyeIndex = Math.random() * 500 ;
}

babyObj.prototype.draw = function()
{
	this.eyeIndex++
	// 小鱼趋向于大鱼
	//ctx1
	this.x = lerpDistance(mom.x ,this.x , 0.99);
	this.y = lerpDistance(mom.y , this.y , 0.99);

	// lerp angle
	var deltaY = mom.y - this.y ; 
	var delatX = mom.x - this.x ; 
	var beta = Math.atan2(deltaY , delatX) + Math.PI; //【-pi ,pi】
	// lerp angle 
	this.angle = lerpAngle(beta , this.angle , 0.6) ;
	ctx1.save();
	ctx1.translate(this.x , this.y);
	ctx1.rotate(this.angle);

	// 计算boby身体颜色变化
	this.babyBodyTimer += deltaTime ; 
	if(this.babyBodyTimer > 300)
	{
		this.babyBodyCount = this.babyBodyCount + 1;
		this.babyBodyTimer %= 300 ;
		if(this.babyBodyCount > 19)
		{
			this.babyBodyCount = 19 ;
			//  game over
			data.gameOver = true ;
		}
	}
	ctx1.drawImage(babyBody[this.babyBodyCount],-babyBody[this.babyBodyCount].width * 0.5 , -babyBody[this.babyBodyCount].height * 0.5);
	ctx1.drawImage(this.babyTail,-this.babyTail.width * 0.5 + 23 , -this.babyTail.height * 0.5);
	
	var eye = this.babyEye ;
	if(this.eyeIndex > this.delayeyeIndex && this.eyeIndex < (this.delayeyeIndex+10) )
		eye = this.babyEye0 ; 
	if(this.eyeIndex >= (this.delayeyeIndex+10))
	{ 
		this.delayeyeIndex = Math.random() * 500 ;
		this.eyeIndex = 0 ;
	}

	ctx1.drawImage(eye,-this.babyEye.width * 0.5 , -this.babyEye.height * 0.5 );
	ctx1.restore();
}