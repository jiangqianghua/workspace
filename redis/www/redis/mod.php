<?php
	require("redis.php");
	$uid = $_GET["id"];
	$data = $redis->hgetall("user:".$uid);

?>
	<form action='doedit.php' method='post'>
		<input type='hidden' name="uid" value="<?php echo $data["uid"] ?>"/>
		�û���:<input type='text' name='username' value="<?php echo $data['username'] ?>"/></br>
		����:<input type='text' name='age' value="<?php echo $data['age'] ?>"/></br>
		<input type='submit' value='�޸�'/>
		<input type='reset' value='����'/>
	</form>