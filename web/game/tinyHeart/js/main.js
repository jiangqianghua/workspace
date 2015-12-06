// body加载完成之后，执行game函数入口
//document.body.onload = game ;
var can1 ; // fishes ,dust , ui , circle
var can2 ; // background, ane , fruits

var ctx1 ; 
var ctx2 ;

var lastTime ;  // 上一次时间
var deltaTime ;  //两帧间隔时间差
window.onload = game ;
//var i = 0 ;

var bgPic = new Image();

var fruit ;
var mom ;
var baby ;

var mx ,my ;

var  babyBody = [];
var momTail = [] ; 
var momBodyOra = [] ; 
var momBodyBlue = [] ;
var data ;
var wave ;
var halo ;
function game()
{
	//console.log("onload test");
	init();
	lastTime = Date.now();
	deltaTime = 0 ;
	gameloop();
}

function init()
{
	// 获得canvas context
	can1 = document.getElementById("canvas1");
	ctx1 = can1.getContext("2d");

	can2 = document.getElementById("canvas2");
	ctx2 = can2.getContext("2d") ;

	bgPic.src = "./src/background.jpg";

	canWidth = can1.width ; 
	canHeight = can1.height ;

	ane = new aneObj();
	ane.init();

	fruit = new fruitObj();
	fruit.init();

	mom = new momObj();
	mom.init();

	baby = new babyObj();
	baby.init();

	mx = canWidth * 0.5 ; 
	my = canHeight * 0.5 ;
	// 监听画笔鼠标移动事件
	can1.addEventListener("mousemove" , onMouseMove , false);

	for(var i = 0 ; i < 20 ; i++)
	{
		babyBody[i] = new Image();
		babyBody[i].src = "./src/babyFade" + i + ".png";
	}

	for(var i = 0 ; i < 8 ; i++)
	{
		momTail[i] = new Image();
		momTail[i].src = "./src/bigTail" + i + ".png";
	}

	for(var i = 0 ; i < 8 ; i++)
	{
		momBodyOra[i] = new Image();
		momBodyBlue[i] = new Image();
		momBodyOra[i].src = "./src/bigSwim" + i + ".png";
		momBodyBlue[i].src = "./src/bigSwimBlue" + i + ".png";
	}
	
	data = new dataObj();

	ctx1.font = "30px Verdana" ;
	ctx1.textAlign = "center";

	wave = new waveObj();
	wave.init();

	halo = new haloObj();
	halo.init();

	can1.addEventListener("touchMoveEvent",touchMove);
}

function touchMoveEvent(e)
{
	trace("aaaa");
}
// 循环刷新
function gameloop()
{
	//requestAnimFrame 会根据科学计算你的刷新频率，有动态时间间隔
	window.requestAnimFrame(gameloop) ;// setInterval , setTimeout
	var now = Date.now();
	deltaTime = now - lastTime;
	lastTime = now ;

	//console.log("gameloop"+deltaTime);
	ctx2.clearRect(0,0,canWidth,canHeight);
	if(deltaTime > 40) deltaTime = 40 ;

	drawBackground(); // 绘制背景
	ane.draw();// 绘制海葵
	fruitMonitior();
	fruit.draw(); // 绘制果实
	ctx1.clearRect(0,0,canWidth,canHeight);
	mom.draw();

	momFruitsCollision();
	momBybyCollision();
	baby.draw();

	data.draw();
	wave.draw();
	halo.draw();
}

function onMouseMove(e){
	if(data.gameOver)
		return ;
	e.preventDefault(); //
	if(e.offSetX || e.layerX)
	{
		mx = e.offSetX == undefined?e.layerX:e.offSetX ;
		my = e.offSetY == undefined?e.layerY:e.offSetY ;
		//console.log(mx + ":" + my);
	}
}