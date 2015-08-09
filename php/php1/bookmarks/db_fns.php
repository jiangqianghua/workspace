<?php
	function db_connect()
	{
		$result = new mysqli('localhost' , 'root' , '150700' , 'bookmarks');
		#echo $result;
		if(!$result)
		{
			throw new Exception("could not connect to database server");
		}
		else
		{
			return $result;
		}
	}
?>