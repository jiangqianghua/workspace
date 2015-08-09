<?php 
	function register($username , $email , $password)
	{
		echo $username . " " . $email . " " . $password ;
		$conn = db_connect();
		$result = $conn->query("select * from users where username='".$username."'");
		
		if(!$result)
		{
			throw new Exception("could not execute query");
		}
		
		if($result->num_row > 0)
		{
			throw new Exception("that username is taken - 
			go back and choose another one.");
		}
		#insert into users values('jiang',sha1('1234'),'"240437339@qq.com')
		$result = $conn->query("insert into users values('".$username."',sha1('".$password."'),'".$email."')");
		
		if(!$result)
		{
			throw new Exception("could not register you database - please tye again later.");
		}
		
		return true;
	}
	
	
	function login($username , $password)
	{
		echo $username ." ".$password;
		$conn = db_connect();
		
		$result = $conn->query("select * from users where username='".$username."' and passwd=sha1('".$password."')");
		if(!$result)
		{
			throw new Exception('could not log you in111');
		}
		
		if($result->num_rows > 0)
		{
			return true ;
		}
		else
		{
			throw new Exception("could not log you in222");
		}
	}
	
	function check_valid_user()
	{
		if(isset($_SESSION['valid_user']))
		{
			echo 'logged in as '.$_SESSION['valid_user'].'.<br />';
		}
		else
		{
			do_html_heading("Problem:");
			echo "you are not logged in.<br />";
			do_html_url("login.php","Login");
			do_html_footer();
			exit;
		}
	}
	
	function change_password($username , $old_password , $new_password)
	{
		login($username , $old_password);
		$conn = db_connect();
		$result = $conn->query("update users set passwd = sha1('".$new_password."') where username='".$username."'");
		if(!$result)
		{
			throw new Exception("password could not be changed");
		}
		else
		{
			return true ;
		}
	}
	
	function reset_password($username)
	{
		$new_password = get_rand_word(6,13);
		if($new_password == false)
		{
			throw new Exception("could not generate new password");
		}
		
		$rand_number = rand(0,999);
		$new_password .=$rand_number ;
		
		$conn = db_connect();
		
		$result = $conn->query("update users set passwd = sha1('".$new_password."') where username = '".$username."'");
		
		if(!result)
		{
			throw new Exception("could not change password");
		}
		else
		{
			return $new_password ;
		}
		
	}
	
	function get_rand_word($min_length, $max_length) {
	// grab a random word from dictionary between the two lengths
	// and return it
	   // generate a random word
	  $word = '';
	  // remember to change this path to suit your system
	  $dictionary = '/usr/share/dict/words';  // the ispell dictionary
	  $fp = @fopen($dictionary, 'r');
	  if(!$fp) {
		return false;
	  }
	  $size = filesize($dictionary);

	  // go to a random location in dictionary
	  $rand_location = rand(0, $size);
	  fseek($fp, $rand_location);

	  // get the next whole word of the right length in the file
	  while ((strlen($word) < $min_length) || (strlen($word)>$max_length) || (strstr($word, "'"))) {
		 if (feof($fp)) {
			fseek($fp, 0);        // if at end, go to start
		 }
		 $word = fgets($fp, 80);  // skip first word as it could be partial
		 $word = fgets($fp, 80);  // the potential password
	  }
	  $word = trim($word); // trim the trailing \n from fgets
	  return $word;
	}
	
	function notify_password($username , $password)
	{
		$conn = db_connect();
		
		$result = $conn->query("select email from users where username='".$username."'");
		
		if(!result)
		{
			throw new Exception("could not find email address");
		}
		else if($result->num_rows == 0)
		{
			throw new Exception("could not find email address");
		}
		else
		{
			$row = $result->fetch_object();
			$email = $row->email ;
			$from = "From: 240437339@qq.com \r\n";
			$mesg = "you phpbookmark password has been changed to ".$password."\r\n";
			if(mail($email , 'phpbookmark login information',$mesg , $from))
			{
				return true ;
			}
			else
			{
				throw new Exception("could not send email");
			}
		}
	}
	
?>