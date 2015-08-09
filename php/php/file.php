<?php
	echo readfile("web.txt");
	$myfile = fopen("web.txt","r") or die(" can not open file");
	echo "<br>";
	echo fread($myfile , filesize("web.txt")) ;
	fclose($myfile);
	echo "<br> use gets read file <br>" ;
	$myfile = fopen("web.txt" , "r") or die("can not open file") ;
	while(!feof($myfile))
	{

		echo fgetc($myfile);
		//echo fgets($myfile);
		//echo fgets($myfile);
	}
	fclose($myfile);
?>
