<html>
	<head>
		<title> insert books</title>
	</head>
	<body>
		<h1>inert books result</h1>
		<?php 
			$isbn = $_POST['isbn'];
			$author = $_POST['author'];
			$title = $_POST['title'];
			$price = $_POST['price'];
			if(!$isbn || !$author || !$title || !$price )
			{
				echo 'you have not entered all the required details.<br />';
				exit;
			}
			
			if(!get_magic_quotes_gpc())
			{
				$isbn = addslashes($isbn);
				$author = addslashes($author);
				$title = addslashes($title);
				$price = addslashes($price);
			}
			
			@ $db = new mysqli('localhost' , 'root' , '150700' , 'books');
			
			if(mysqli_connect_errno())
			{
				echo 'Error: Could not connect to database. Please try again later!';
				exit ;
			}
			
			$query = "insert into books values
					 ('".$isbn."', '".$author."', '".$title."', '".$price."')" ;
			$result = $db->query($query);
			
			if($result)
			{
				echo $db->affected_rows." book insert into database.";
			}
			else
			{
				echo 'an Error has occurred . The item was not added.';
			}
			
			$db->close();
		?>
	</body>
</html>