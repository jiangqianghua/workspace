<?php 
	
	session_start();
	
	if(isset($_POST['userid']) && isset($_POST['password']))
	{
		$userid = $_POST['userid'];
		$password = $_POST['password'];
		
		if($userid == 'jiang' && $password == '123456')
		{
			$_SESSION['valid_user'] = $userid ;
		}
	}

?>

<html>
	
	<head>
		<title>home page</title>
	</head>

	<body>
		<h1> home page</h1>
		<?php
			if(isset($_SESSION['valid_user']))
			{
				echo 'you are logged in as'.$_SESSION['valid_user'].'<br>';
				echo '<a href ="logout.php">log out</a><br/>';
			}
			else 
			{
				if(isset($userid))
				{
					echo 'could not log you in.<br>';
				}
				else
				{
					echo 'you are not logged in.<br>';
				}
				
				echo '<form method="post" action="authmain.php">';
				echo '<table>';
				echo '<tr><td>Userid:</td>';
				echo '<td><input type="text" name="userid"</td></tr>';
				echo '<tr><td>password:</td>';
				echo '<td><input type="password" name="password"></td></tr>';
				echo '<tr><td colspan="2" align="center">';
				echo '<input type="submit" value="log in"></td></tr>';
				echo '</table></form>';
			}
		?>
		<a href="members_only.php">Members section</a>
	</body>
</html>