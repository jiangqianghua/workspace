<?php
//i$name=$_POST['name'];
//$email=$_POST['email'];
//$feedback=$_POST['feedbak'];

$toaddress = '240437339@qq.com';
$subject = 'feed back from web site';
$maincontent = 'hello i love you';

$fromaddress='frome 240437339@qq.com';

mail($toaddress , $subject , $maincontent , $fromaddress);
?>
