<?php
	// ʵ����redis
	$redis = new Redis();
	// ���ӷ�����
	$redis->connect("localhost",6379);
	
	// ��Ȩ
	$redis->auth('150700');
	// ��ӡ
	#var_dump($redis->keys("*"));
	
	//echo'<br>';
	
	//$redis->set("phpname","php");
	#var_dump($redis->get("phpname"));
	
?>