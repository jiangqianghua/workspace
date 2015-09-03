<?php
	// 实例化redis
	$redis = new Redis();
	// 连接服务器
	$redis->connect("localhost",6379);
	
	// 授权
	$redis->auth('150700');
	// 打印
	#var_dump($redis->keys("*"));
	
	//echo'<br>';
	
	//$redis->set("phpname","php");
	#var_dump($redis->get("phpname"));
	
?>