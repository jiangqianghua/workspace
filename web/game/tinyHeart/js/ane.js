// 绘制海葵对象
var aneObj = function()
{
	this.x = [] ; 
	this.len = [] ;
}

aneObj.prototype.num = 50 ; 
// 初始化
aneObj.prototype.init = function(){
	for(var i = 0 ; i < this.num ; i++)
	{
		this.x[i] = i * 16 + Math.random()*20;//[0,1)
		this.len[i] = 200 + Math.random()*50 ;
	}
}
// 绘制
aneObj.prototype.draw = function (){
	ctx2.save();  // 表示下面设置的api只在这一段之间起作用
	ctx2.globalAlpha = 0.6 ;
	ctx2.strokeStyle = "purple";
	ctx2.lineCap = "round";
	ctx2.lineWidth = 20 ;
	ctx2.strokeStyle = "#3b154e";
	for(var i = 0 ; i < this.num ; i++)
	{
		// 需要用到的api
		// beginPath moveTo 开始坐标， LineTo 结尾坐标 stroke 开始绘制，
		//strokeStyle 线条样式, lineWidth 线条宽度 ,lineCap 线条边样式 ,
		//globalAlpha 透明度
		ctx2.beginPath();
		ctx2.moveTo(this.x[i] , canHeight);
		ctx2.lineTo(this.x[i] ,canHeight - this.len[i]);
		ctx2.stroke();

	}
	ctx2.restore();
}
