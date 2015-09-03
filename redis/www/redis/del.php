<?php 
	require('redis.php');
	$uid = $_GET['id'];
	$redis->del("user:".$uid);
	header('location:userlist.php');
?>