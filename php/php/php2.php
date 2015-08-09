<html>
	<head></head>
	<body>
		<?php
			$name = $email = $gender = $comment = $website = "" ;
			$nameErr = $emailErr = $genderErr = $websiteERR = "" ;
			if($_SERVER["REQUEST_METHOD"] == "POST"){
				$name = test_input($_POST["name"]) ;
				if(empty($name))
				{
					$nameErr = "name null" ;
				}
				else if(!preg_match("/^[a-zA-Z]*$/",$name))
				{
					$nameErr = "name validate";
				}
				$email = test_input($_POST["email"]) ;
				if(empty($email))
				{
					$emailErr = "email null" ;
				}
				else if(!preg_match("/([\w\-]+\@[\w\-]+\.[\w\-]+)/",$email))
				{
					$emailErr = "email validate" ;
				}
				$website = test_input($_POST["website"]) ;
				if(empty($website))
				{
					$websiteErr = "website null" ;
				}
				else if(!preg_match("/\b(?:(?:https?|ftp):\/\/|www\.)[-a-z0-9+&@#\/%?=~_|!:,.;]*[-a-z0-9+&@#\/%
=~_|]/i",$website))
				{
					$websiteErr = " email validate " ;
					
				}
				$comment = test_input($_POST["comment"]) ;
				$gender = test_input($_POST["gender"]) ;
				if(empty($gender))
				{
					$genderErr = "gender null" ;
				}
			}
			
			function test_input($data){
				$data = trim($data);	
				$data = stripslashes($data) ;
				$data = htmlspecialchars($data);
				return $data ;
			}
		?>
		
		<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>">
			name: <input type="text" name="name" value="<?php echo $name ; ?>">
			<span><?php echo $nameErr; ?></span>
			<br><br>
			email <input type="text" name="email" value="<?php echo $email ; ?>">
			<span><?php echo $emailErr; ?></span>
			<br><br>
			website <input type="text" name="website" value="<?php echo $website ?>">
			<span><?php echo $websiteErr; ?></span>
			<br><br>
			comment <input type="text" name="comment" value="<?php echo $comment ?>">
			<br><br>
			gender
			<input type="radio" name="gender" value="female" <?php if(isset($gender) && $gender == "female") echo "checked"; ?>> female
			<input type="radio" name="gender" value="male" <?php if(isset($gender) && $gender=="male") echo "checked"; ?>> male
			<span><?php echo $genderErr; ?></span>
			<br><br>
			<input type="submit" name="submit" value="submit" />
		</form>
		
		<?php
			echo "you input :<br>";
			echo $name,"<br>" ;	
			echo $email,"<br>" ;
			echo $website,"<br>" ;
			echo $comment,"<br>" ;
			echo $gender,"<br>" ;
			echo "today:",date("Y/m/d"),"<br>";
			echo "today:",date("Y.m.d"),"<br>";
			echo "today:",date("Y-m-d"),"<br>";
			echo "today:",date("l"),"<br>";
			echo "2010-",date("Y"),"<br>";
			echo "time :" ,date("h:i,sa"),"<br>";
			date_default_timezone_set("Asia/Shanghai");
			echo "time :", date("h:i:sa"),"<br>"
		?>
	</body>
</html>



