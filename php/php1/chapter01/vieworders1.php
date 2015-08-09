<?php
$orders = file('orders.txt');
$num = count($orders);
echo $num .'<br />';

for($i=0;$i<$num;$i++)
{
	//echo $orders[$i].'<br />';
	$line = explode(' ',$orders[$i]);
	//echo $line[0];
	$price = intval($line[0]);
	echo $price.'<br />';
}
?>
