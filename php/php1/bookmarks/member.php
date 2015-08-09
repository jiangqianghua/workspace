<?php 
	require_once("bookmark_fns.php");
	session_start();
	
	$username = $_POST['username'];
	$passwd = $_POST['passwd'];
	
	if($username && $passwd)
	{
		try
		{
			login($username , $passwd);
			$_SESSION['valid_user'] = $username ;
		}
		catch(Exception $e)
		{
			do_html_header("Problem:");
			echo 'you could not be logged in. you must be logged in to view this page '.$e->getMessage();
			do_html_url("login.php",'Login');
			do_html_footer();
			exit;
		}
	}
	
	do_html_header("Home");
	check_valid_user();
	
	do_html_url("logout.php",'Log out');
	do_html_url("change_passwd_form.php",'change password');
	
	display_user_menu();
	do_html_footer();
	
?>