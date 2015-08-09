<html>
<body>
	<form method="post" action="<?php echo $_SERVER['PHP_SELF'];?>">
	name:<input type="text" name="fname">
	<input type="submit">
		
	</form>
	<a href = "php1.php?age=26">age</a>

<?php
	$name = $_REQUEST['fname'];
	echo $name ;
	echo "<br>"
	echo "----<br>" ;
	$age = $_GET['age'];
	echo $age ;
?>
</body>
</html>
