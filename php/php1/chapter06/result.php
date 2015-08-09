<html>
	<head>
		<title> books result</title>
	</head>
	<body>
		<h1> books result</h1>
		<?php
			$searchtype = $_POST['searchtype'];
			$searchterm = $_POST['searchterm'];
			echo $searchterm . '  ' . $searchtype;
			if(!$searchtype || ! $searchterm)
			{
				echo 'you hava not enered search datail . please go back and try again!';
				exit ;
			}
			
			if(!get_magic_quotes_gpc())
			{
				$searchterm = addslashes($searchterm);
				$searchtype = addslashes($searchtype);
			}
			
			@ $db = new mysqli('localhost','root' , '150700' , 'books');
			
			if(mysqli_connect_errno())
			{
				echo 'Error:Could not connect to databases , please try again later.';
				exit ;
			}
			
			$query = "select * from books where ".$searchtype." like '%".$searchterm."%'";
			echo $query;
			$result = $db->query($query);
			
			$num_results = $result->num_rows ;
			echo "<p> number of books found:".$num_results."</p>";
			
			for ($i=0 ; $i < $num_results ; $i++)
			{
				$row = $result->fetch_assoc();
				echo '<p><strong>'.($i + 1).'. Title:';
				echo htmlspecialchars(stripslashes($row['title']));
				echo '</strong><br />Author: ';
				echo stripcslashes($row['author']);
				echo '<br /> ISBN: ';
				echo stripcslashes($row['isbn']);
				echo '<br /> Prices: ';
				echo stripcslashes($row['price']);
				echo '</p>';
			}
			
			$result->free();
			$db->close();
		?>
	</body>
</html>