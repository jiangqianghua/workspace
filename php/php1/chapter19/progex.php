<?php 
	chdir("/var/www/upload/");
	echo "<pre>" ;
	
	exec("ls -la" , $result);
	
	foreach($result as $line)
	{
		echo "$line\n";
	}
	
	echo "<pre>";
	echo "<br><br>";
	passthru("ls -a");

?>