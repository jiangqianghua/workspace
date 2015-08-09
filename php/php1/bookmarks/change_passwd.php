<?php 
	require_once("bookmark_fns.php");
	
	session_start();
	
	do_html_header("changing password");
	
	$old_passwd = $_POST['old_passwd'];
	$new_passwd = $_POST['new_passwd'];
	$new_passwd2 = $_POST['new_passwd2'];
	
	try
	{
		check_valid_user();
		if(!filled_out($_POST))
		{
			throw new Exception("you have not filled out the form completely , please try again");
		}
		
		if($new_passwd != $new_passwd2)
		{
			throw new Exception("passwords entered were not the same. not change.");
		}
		
		if((strlen($new_passwd) > 16) || (strlen($new_passwd) < 6))
		{
			throw new Exception("new passwod must be between 6 and 16 characters, try again");
		}
		
		change_password($_SESSION['valid_user'],$old_passwd,$new_passwd);
		echo 'password changed';
	}
	catch(Exception $e)
	{
		echo $e->getMessage();
	}
	#disp_user_menu();
	do_html_footer();
?>