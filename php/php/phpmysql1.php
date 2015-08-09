<?php
	echo "start connect mysql<br>";
	$con = mysql_connect("localhost" , "root" , "150700");
	if(!$con)
	{
		die("could not connect:".mysql_error());
		echo "connect fail";
	}
	else
	{
		echo "connect success!" ;
	}
	// create databases
//	if(mysql_query("create database phpdb" , $con))
//	{
//		echo "databases create<br>"; 
//	}
//	else
//	{
//		echo "Error createing database:".mysql_error()."<br>";
//	}
//
//	//create dabase tables
//	mysql_select_db("phpdb",$con) ;
//	$sql = "create table Person
//	(
//		FirstName varchar(15),
//		LastName varchar(15),
//		Age int
//	)"	;
//	if(mysql_query($sql,$con))
//	{
//		echo "create person table success!<br>";
//	}
//	else
//	{
//		echo "create person faile.".mysql_error()."<br>";
//	}
//	
//	$addPerson = "insert into Person (FirstName , LastName , Age) values('jiang1','qianghua1',26)" ;
//	if(mysql_query($addPerson,$con))
//	{
//		echo "add success <br>" ;
//	}
//	else
//	{
//		echo "add fail.".mysql_error()."<br>";
//	}
	function querydb($con)
	{
		mysql_select_db("phpdb",$con) ;
        	$query1 = "select * from Person";
        	$result = mysql_query($query1,$con);
       		 while($row = mysql_fetch_array($result))
        	{
                	echo $row['FirstName']." ".$row['LastName'].$row['Age']."<br>";
        	}
	}
	echo "<br>" ;
	mysql_select_db("phpdb",$con) ;
	$query1 = "select * from Person";
	$result = mysql_query($query1,$con);
	while($row = mysql_fetch_array($result))
	{
		echo $row['FirstName']." ".$row['LastName'].$row['Age']."<br>";
	}

	$update1 = "update Person set age='101' where FirstName = 'jiang'";
	mysql_query($update1);
	echo "update over<br>";
	querydb($con);
	
	$delete1 = "delete from Person where FirstName='jiang'" ;
	mysql_query($delete1 , $con);
	echo "delete <br>";
	querydb($con);

	mysql_close($con);
?>

