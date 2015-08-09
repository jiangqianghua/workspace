<?php 
	session_start();
	$old_user = $_SESSION['valid_user'];
	unset($_SESSION['valid_user']);
	session_destroy();
?>
<html>
	<head>
		<title> log out</title>
	</head>
	<body>
		<h1> log out </h1>
		<?php
			if($_empty($old_user))
			{
				echo 'logged out.<br>';
			}
			else
			{
				echo 'you are not logged in ,adn so have not been logged out.<br>';
			}
		?>
		<a href="authmain.php">back to main page</a>
	</body>
</html>