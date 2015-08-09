<?php
	class Car
	{
		var $color ;
		function Car($color = "green"){
			$this->color = $color ;	
		}
		function what_color(){
			$this->color ;
		}
	}
	function print_vars($obj){
		foreach (get_object_vars($obj) as $prop => $val){
			echo "\t$prop = $val\n" ;
		}
	}
	
	function myTest(){
		static $x = 0 ;	
		echo $x ;
		$x++ ;
	}
	function myTest1(){
		$txt1 = "learn php";
		$txt2 = "koolearn.com";
		$cars = array("jiang","qianghua","hua");
		
		echo $txt1 ;
		echo "<br>";
		echo "study php at $txt2" ;
		echo "my car is a {$cars[0]}";
	}
	myTest();
	myTest();
	myTest();
	myTest1();
?>

