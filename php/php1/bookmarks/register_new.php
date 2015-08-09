<?php 
	require_once("bookmark_fns.php");
	
	$email = $_POST['email'];
	$username = $_POST['username'];
	$passwd = $_POST['passwd'];
	$passwd2 = $_POST['passwd2'];
	
	session_start();
	
	try
	{
		if(!filled_out($_POST))
		{
			throw new Exception('you have not filled the form out currectly-
			please go back and try');
		}
		
		if(!valid_email($email))
		{
			throw new Exception('that is not a valid email address. please -
			go back and try again.');
		}

		if($passwd != $passwd2)
		{
			throw new Exception('the password you enter do not match-
			.please go back and try again.');
		}
		
		if((strlen($passwd) < 6) || (strlen($passwd2)) > 16)
		{
			throw new Exception('your password must be between 6 and 16 characters.-
			please go back and try again.');
		}
		
		register($username , $email , $passwd);
		
		$_SESSION['valid_user'] = $username ;
		
		do_html_header("Registration successful");
		
		echo 'your registration was successful , go to the members page to start -
		setting up your bookmarks!';
		do_html_footer();
	}
	catch(Exception $e)
	{
		do_html_header("Problem");
		echo $e->getMessage();
		do_html_footer();
		exit;
	}
?>