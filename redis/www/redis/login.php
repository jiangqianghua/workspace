
<?php
	require("redis.php");
	$username = $_POST["username"];
	$pass = $_POST["password"];
	$id = $redis->get("username:".$username);
	
	if(!empty($id)){
		echo "user:".$id ;
		$password = $redis->hget("user:".$id , 'password');
		echo $password .'<br\>';
	//	echo md5($pass);
		if(md5($pass) == $password)
		{
			$auth = md5(time().$username.rand());
			$redis->set("auth".$auth , $id);
			setcookie("auth",$auth,time()+86400);
			header('location:userlist.php');
		}
	}
?>
<form action='' method='post'>
	username:<input type='text' name='username'/> <br/>
	username:<input type='password' name='password'/> <br/>
	<input type = "submit" value='login' />
</form>