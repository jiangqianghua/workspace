<?php
	require("redis.php");
	$id = $_GET[id];
	$uid = $_GET["uid"];
	
	$redis->sadd("user:".$uid."flowing",$id);
	$redis->sadd("user:".$id.":flowers",$uid);
	header('location:userlist.php');
?>