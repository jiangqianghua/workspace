<html>
	<head>
		<title> Stock Quote From ...</title>
	</head>
	<body>
		<?php 
			$symbol = "AMZN";
			echo "<h1> $symbol </h1>" ;
			#$url = 'http://finance.yahoo.com/d/quotes.csv'.'?s='.$symbol.'$e=.csv&f=s11dlt1c1ohgv';
			$url = 'http://www.baidu.com' ;
			echo $url;
			echo "<hr>";
			$contents = file_get_contents($url) ;
			echo "'".$contents."'"
		?>
	</body>
<html>