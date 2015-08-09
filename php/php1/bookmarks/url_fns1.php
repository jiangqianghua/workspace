<?php 
	require_once('bookmark_fns.php');
	session_start();
	
	$new_url = $_POST['new_url'];
	do_html_header("Adding bookmarks");
	
	try
	{
	
		check_valid_user();
	}
	catch(Exception $e)
	{
		echo $e->getMessage();
	}
	
	display_user_menu();
	
	do_html_footer();
?>