// 碰撞检测，检测大鱼和果实的距离
function momFruitsCollision(){
	if(data.gameOver)
		return ;
	for( var i = 0 ; i < fruit.num ; i++)
	{
		if(fruit.alive[i])
		{
			// calculate length
			var l = calLength2(fruit.x[i] ,fruit.y[i] , mom.x , mom.y);
			if(l < 900)
			{
				// fruits eat 
				fruit.dead(i);
				data.fruitNum++ ;

				mom.momBodyCount++ ; 
				if(mom.momBodyCount > 7)
					mom.momBodyCount = 7 ;
				if(fruit.fruitType[i] == "blue")
				{
					data.double = 2 ; 
				}

				wave.born(fruit.x[i] , fruit.y[i]);

			}
		}
	}
}

// mom baby collision
function momBybyCollision(){
	if(data.fruitNum <= 0 || data.gameOver)
		return ;
	var l = calLength2(mom.x , mom.y , baby.x , baby.y) ;
	if(l < 900)
	{
		baby.babyBodyCount = 0 ;
		// data = 0
		data.reset();

		mom.momBodyCount = 0 ;

		// score update 
		data.addScore();

		halo.born(mom.x , mom.y);
	}
}
