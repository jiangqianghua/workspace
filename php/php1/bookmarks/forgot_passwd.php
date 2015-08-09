<?php 

	require_once("bookmark_fns.php");
	
	do_html_header("Resetting password");
	
	$username = $_POST["username"];
	
	try
	{
		$password = reset_password($username);
		notify_password($username , $password);
		echo "your new password has been emailed to you.<br/>";
	}
	catch(Exception $e)
	{
		echo "your new password could not  emailed to you.<br />".$e->getMessage();
	}
	
	do_html_url("login.php","Login");
	do_html_footer();
?>