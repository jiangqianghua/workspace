<?php
@ $fp = fopen("orders.txt","r");
if($fp)
{
	while(!feof($fp))
	{
		$result = fgets($fp , 1024);
		echo $result."<br />";
		
	}	
}
else
{
	echo "read orders.txt error";
}

?>
