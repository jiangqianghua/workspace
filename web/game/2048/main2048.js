var board = new Array();
var score = 0 ;
var hasConficted = new Array();

var startx ,starty , endx , endy ;
$(document).ready(function(){
	prepareForMobile();
	newgame();
});

function prepareForMobile()
{
	if(documentWidth > 500){
		gridContainerWidth = 500 ;
		cellSpace = 20 ; 
		cellSideLength = 100 ;
	}
	$("#grid-container").css("width" , gridContainerWidth - 2*cellSpace);
	$("#grid-container").css("height" , gridContainerWidth - 2*cellSpace);
	$("#grid-container").css("padding" , cellSpace);
	$("#grid-container").css("border-radius" , 0.02*gridContainerWidth);

	$(".grid-cell").css("width",cellSideLength);
	$(".grid-cell").css("height",cellSideLength);
	$(".grid-cell").css("border-radius",0.02*cellSideLength);

}

function newgame(){

	// 初始化棋盘格
	init();
	// 随机两个格子生成数字
	generateOneNumber();
	generateOneNumber();
}

function init(){
	for(var i = 0 ; i < 4 ; i++ )
	{
		board[i] = new Array();
		hasConficted[i] = new Array();
		for(var j = 0 ; j < 4 ; j++ )
		{
			board[i][j] = 0 ;
			hasConficted[i][j] = false ;
			var gridCell = $("#grid-cell-"+i+"-"+j);
			gridCell.css('top',getPosTop(i,j));
			gridCell.css('left',getPosLeft(i,j));
		}

	}

	updateBoardView();
	score = 0 ;
}

function updateBoardView()
{
	$(".number-cell").remove();
	for(var i = 0 ; i < 4 ; i++ )
		for(var j = 0 ; j < 4 ; j++ )
		{
			$("#grid-container").append('<div class="number-cell" id="number-cell-'+i+'-'+j+'"></div>')
			var theNumberCell = $('#number-cell-'+i+'-'+j);
			if(board[i][j] == 0)
			{
				theNumberCell.css('width','0px');
				theNumberCell.css('height','0px');
				theNumberCell.css('top',getPosTop(i,j) + cellSideLength/2);
				theNumberCell.css('left',getPosLeft(i,j) + cellSideLength/2);
			}
			else
			{
				theNumberCell.css('width',cellSideLength);
				theNumberCell.css('height',cellSideLength);
				theNumberCell.css('top',getPosTop(i,j));
				theNumberCell.css('left',getPosLeft(i,j));
				theNumberCell.css('background-color',getNumberBackgroundColor(board[i][j]));
				theNumberCell.css('color',getNumberColor(board[i][j]));
				theNumberCell.text(board[i][j]);
			}

			hasConficted[i][j] = false ;
		}
		$(".number-cell").css('line-height',cellSideLength+"px");
		$(".number-cell").css('font-size',0.6*cellSideLength+"px");

}

function generateOneNumber()
{
	if(nospace(board))
		return false ; 
	// 随机一个位
	var randx = getRand44Num() ;
	var randy = getRand44Num() ;

	var times = 0 ;
	while(times < 50){
		if(board[randx][randy] == 0)
			break;

		var randx = getRand44Num() ;
		var randy = getRand44Num() ;
		times++ ;
	}

	if(times == 50)
	{
		for(var i = 0 ; i < 4 ; i++ )
		{
			for(var j = 0 ; j < 4 ; j++ )
			{
				if(board[i][j] == 0)
				{
					randx = i ; 
					randy = j ;
				}

			}
		}
	}

	// 随机一个数字
	var randNumber = getRand24Num();
	board[randx][randy] = randNumber;
	showNumberWithAnimation(randx , randy , randNumber);
	return true ;
}
// 监听手指手指滑动事件
document.addEventListener('touchstart',function(event){
	startx = event.touches[0].pageX ; 
	starty = event.touches[0].pageY ;
});

document.addEventListener("touchmove",function(event){
	event.preventDefault();
});
document.addEventListener('touchend',function(event){
	endx = event.changedTouches[0].pageX ; 
	endy = event.changedTouches[0].pageY ;

	var deltax = endx - startx ; 
	var deltay = endy - starty ;

	if(Math.abs(deltax) < 0.3*documentWidth &&
		Math.abs(deltay) < 0.3*documentWidth)
		return ;
	// x
	if(Math.abs(deltax) >= Math.abs(deltay))
	{
		// x
		if(deltax > 0)
		{
			// move right
			if(moveRight(board)){
				generateOneNumber();
				isgameover();
			}
		}
		else
		{
			// move left
			if(moveLeft(board)){
				generateOneNumber();
				isgameover();
			}
		}
	}
	else
	{
		// y
		if(deltay > 0)
		{
			// move down
			if(moveDown(board)){
				generateOneNumber();
				isgameover();
			}
		}
		else
		{
			// move up
			if(moveUp(board)){
				generateOneNumber();
				isgameover();
			}
		}
	}
});

// 监听鼠标事件
$(document).keydown(function(event){
	//event.preventDefault(); // 阻挡网页滚动条效果
	switch(event.keyCode){
		case 37: // left
			event.preventDefault(); //
			if(moveLeft(board)){
				generateOneNumber();
				isgameover();
			}
			break ;
		case 38: // up
			event.preventDefault(); //
			if(moveUp(board)){
				generateOneNumber();
				isgameover();
			}
			break;
		case 39: // right
			event.preventDefault(); //
			if(moveRight(board)){
				generateOneNumber();
				isgameover();
			}
			break;
		case 40: // down
			event.preventDefault(); //
			if(moveDown(board)){
				generateOneNumber();
				isgameover();
			}
			break;
		default:
			break;
	}
});

function moveLeft(board){
	if(!canMoveLeft(board))
		return false ;

	for(var i = 0 ; i < 4 ; i++ )
		for(var j = 1 ; j < 4 ; j++ )
		{
			if(board[i][j] != 0)
			{
				for(var k = 0 ; k < j ; k++)
				{
					if(board[i][k] == 0 && noBlockHorizontal(i,k,j,board))
					{
						// move
						showMoveAnimation(i,j,i,k);
						board[i][k] = board[i][j];
						board[i][j] = 0 ;
						continue ;
					}
					else if(board[i][k] == board[i][j] && noBlockHorizontal(i,k,j,board) && !hasConficted[i][k])
					{
						// move  add
						showMoveAnimation(i,j,i,k);
						board[i][k] += board[i][j];
						board[i][j] = 0 ;
						score += board[i][k] ;
						updateScore(score);
						hasConficted[i][k] = true ;
						continue ;
					}
				}
			}
		}
	setTimeout("updateBoardView()",200);
	return true ;
}


function moveRight(board){
	if(!canMoveRight(board))
		return false ;

	for(var i = 0 ; i < 4 ; i++ )
		for(var j = 2 ; j >= 0 ; j-- )
		{
			if(board[i][j] != 0)
			{
				for(var k = 3 ; k > j ; k--)
				{
					if(board[i][k] == 0 && noBlockHorizontal(i,k,j,board))
					{
						// move
						showMoveAnimation(i,j,i,k);
						board[i][k] = board[i][j];
						board[i][j] = 0 ;
						continue ;
					}
					else if(board[i][k] == board[i][j] && noBlockHorizontal(i,k,j,board) && !hasConficted[i][k])
					{
						// move  add
						showMoveAnimation(i,j,i,k);
						board[i][k] += board[i][j];
						board[i][j] = 0 ;
						score += board[i][k] ;
						updateScore(score);
						hasConficted[i][k] = true ;
						continue ;
					}
				}
			}
		}
	setTimeout("updateBoardView()",200);
	return true ;
}

function moveUp(board){
	if(!canMoveUp(board))
		return false ;

	for(var j = 0 ; j < 4 ; j++ )
		for(var i = 1 ; i < 4 ; i++ )
		{
			if(board[i][j] != 0)
			{
				for(var k = 0 ; k < i ; k++)
				{
					if(board[k][j] == 0 && noBlockVertical(j,i,k,board))
					{
						// move
						showMoveAnimation(i,j,k,j);
						board[k][j] = board[i][j];
						board[i][j] = 0 ;
						continue ;
					}
					else if(board[k][j] == board[i][j] && noBlockVertical(j,i,k,board) && !hasConficted[k][j])
					{
						// move  add
						showMoveAnimation(i,j,k,j);
						board[k][j] += board[i][j];
						board[i][j] = 0 ;
						score += board[k][j] ;
						updateScore(score);
						hasConficted[k][j] = true ;
						continue ;
					}
				}
			}
		}
	setTimeout("updateBoardView()",200);
	return true ;
}

function moveDown(board){
	if(!canMoveDown(board))
		return false ;

	for(var j = 0 ; j < 4 ; j++ )
		for(var i = 2 ; i >= 0 ; i-- )
		{
			if(board[i][j] != 0)
			{
				for(var k = 3 ; k > i ; k--)
				{
					if(board[k][j] == 0 && noBlockHorizontal(j,i,k,board))
					{
						// move
						showMoveAnimation(i,j,k,j);
						board[k][j] = board[i][j];
						board[i][j] = 0 ;
						continue ;
					}
					else if(board[k][j] == board[i][j] && noBlockHorizontal(j,i,k,board) && !hasConficted[k][j])
					{
						// move  add
						showMoveAnimation(i,j,k,j);
						board[k][j] += board[i][j];
						board[i][j] = 0 ;
						score += board[k][j] ;
						updateScore(score);
						hasConficted[k][j] = true ;
						continue ;
					}
				}
			}
		}
	setTimeout("updateBoardView()",200);
	return true ;
}

function isgameover()
{
	if(nospace(board) && nomove(board))
	{
		gameover();
	}
}

function gameover(){
	alert("game over!");
}

