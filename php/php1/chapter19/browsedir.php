<html>
	<head>
		<title> browse directories</title>
	</head>
	<body>
		<h1>browsing</h1>
		<?php 
			$current_dir = '/var/www/upload/';
			$dir = opendir($current_dir);
			echo "<p> Upload directoty is &current_dir</p>" ;
			echo "<p> Directory Listing:</p><ul>";
			
			while(false !== ($file = readdir($dir)))
			{
				if($file != "." && $file != "..")
				{
					echo "<li>$file</li>";
				}
			}
			echo "</ul>";
			closedir($dir);
		?>
	</body>
<html>