<?php

$tireqty = $_POST['tireqty'];
$oilqty = $_POST['oilqty'];
$sparkqty = $_POST['sparkqty'];

echo $tireqty .'tires<br />';
echo $oilqty .' bottle od oil<br />';
echo $sparkqty .' spark plugs <br />';

define('TIREPRICE',100);
define('OILPRICE',10);
define('SPARKPRICE',4);

$totalamount = $tireqty * TIREPRICE + $oilqty * OILPRICE + $sparkqty * $SPARKPRICE ;
if($totalamount == 0)
{
	echo "you did not order anything on the previous page!<br/>";
}
else
{
	echo 'Subtotal: $'.number_format($totalamount,2)."<br/>";

	$taxrate = 0.10 ;
	$totalamount = $totalamount *(1 + $taxrate);
	echo "total include tax: $".number_format($totalamount,2)."<br />";
	//phpinfo();
}
$output = 'tireqty:'.$tireqty.' oilqty:'.$oilqty.' sparkqty:'.$sparkqty ;
// write in file
$documentroot = $_SERVER["DOCUMENT_ROOT"];
//echo $documentroot  // /var/www
//@ $fp = fopen("$documentroot/php1/chapter01/orders.txt"."w");
$fp = fopen("orders.txt","w");
flock($fp ,LOCK_EX);
if(!$fp)
{
	echo "open orders.txt error";
}
else
{
	fwrite($fp , $output , strlen($output));
	
}
flock($fp , LOCK_UN);
fclose($fp)

?>
