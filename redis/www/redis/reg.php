<?php
	require('redis.php');
	$username = $_POST['username'];
	$password = md5($_POST['password']);
	$age = $_POST['age'];
	$uid = $redis->incr("userid");
	$redis->hmset("user:".$uid , array('uid'=>$uid , 'username'=>$username ,'password'=>$password , 'age'=>$age));
	
	header('location:userlist.php');
?>