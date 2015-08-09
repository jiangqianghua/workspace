<?php 
	function do_html_header($title)
	{
?>
	<html>
		<head>
			<title><?php echo $title;?></title>
			<link href="css/headerStyle.css" rel=stylesheet type="text/css">
		</head>
		<body>
			<img src="bookmark.gif" alt="PHPbookmark logo" align="left" />
			<h1>PHPbookmark</h1>
			<hr />
<?php
		if($title)
		{
			do_html_heading($title);
		}
	}
?>

<?php
	function do_html_heading($title)
	{
?>
		<h2><?php  echo $title;?></h2>
<?php 
	}
?>

<?php 
	function display_site_info()
	{
?>
		<ul>
		<li>Store your bookmarks online with us!</li>
		<li>See what other users use!</li>
		<li>Share your favorite links with others!</li>
		</ul>
<?php 	
	}
?>

<?php
	function do_html_footer()
	{
?>
		</body>
		</html>
<?php	
	}
?>

<?php 
	function display_login_from()
	{
?>
  <p><a href="register_form.php">Not a member?</a></p>
  <form method="post" action="member.php">
	  <table bgcolor="#cccccc">
	   <tr>
		 <td colspan="2">Members log in here:</td>
	   <tr>
		 <td>Username:</td>
		 <td><input type="text" name="username"/></td></tr>
	   <tr>
		 <td>Password:</td>
		 <td><input type="password" name="passwd"/></td></tr>
	   <tr>
		 <td colspan="2" align="center">
		 <input type="submit" value="Log in"/></td></tr>
	   <tr>
		 <td colspan="2"><a href="forgot_form.php">Forgot your password?</a></td>
	   </tr>
	 </table></form>
<?php	
	}
?>

<?php 
	function display_registration_form()
	{
?>
	 <form method="post" action="register_new.php">
	 <table bgcolor="#cccccc">
	   <tr>
		 <td>Email address:</td>
		 <td><input type="text" name="email" size="30" maxlength="100"/></td></tr>
	   <tr>
		 <td>Preferred username <br />(max 16 chars):</td>
		 <td valign="top"><input type="text" name="username"
			 size="16" maxlength="16"/></td></tr>
	   <tr>
		 <td>Password <br />(between 6 and 16 chars):</td>
		 <td valign="top"><input type="password" name="passwd"
			 size="16" maxlength="16"/></td></tr>
	   <tr>
		 <td>Confirm password:</td>
		 <td><input type="password" name="passwd2" size="16" maxlength="16"/></td></tr>
	   <tr>
		 <td colspan=2 align="center">
		 <input type="submit" value="Register"></td></tr>
	 </table></form>
<?php 	
	}
?>

<?php
 function do_html_url($url , $name)
 {
?>
	<br /><a href="<?php echo $url;?>"><?php echo $name;?></a><br />
<?php  
 }
?>

<?php 
	function display_password_form() 
	{	
?>
	   <br />
	   <form action="change_passwd.php" method="post">
	   <table width="250" cellpadding="2" cellspacing="0" bgcolor="#cccccc">
	   <tr><td>Old password:</td>
		   <td><input type="password" name="old_passwd"
				size="16" maxlength="16"/></td>
	   </tr>
	   <tr><td>New password:</td>
		   <td><input type="password" name="new_passwd"
				size="16" maxlength="16"/></td>
	   </tr>
	   <tr><td>Repeat new password:</td>
		   <td><input type="password" name="new_passwd2"
				size="16" maxlength="16"/></td>
	   </tr>
	   <tr><td colspan="2" align="center">
		   <input type="submit" value="Change password"/>
	   </td></tr>
	   </table>
	   <br />
<?php
	}
?>

<?php 
	function display_forgot_form() {
?>
	   <br />
	   <form action="forgot_passwd.php" method="post">
	   <table width="250" cellpadding="2" cellspacing="0" bgcolor="#cccccc">
	   <tr><td>Enter your username</td>
		   <td><input type="text" name="username" size="16" maxlength="16"/></td>
	   </tr>
	   <tr><td colspan=2 align="center">
		   <input type="submit" value="Change password"/>
	   </td></tr>
	   </table>
	   <br />
<?php
	}
?>

<?
	function display_user_menu() {
?>
		<hr />
		<a href="member.php">Home</a> &nbsp;|&nbsp;
		<a href="add_bm_form.php">Add BM</a> &nbsp;|&nbsp;
<?php
		 // only offer the delete option if bookmark table is on this page
		global $bm_table;
		if ($bm_table == true) {
		echo "<a href=\"#\" onClick=\"bm_table.submit();\">Delete BM</a> &nbsp;|&nbsp;";
		} else {
		echo "<span style=\"color: #cccccc\">Delete BM</span> &nbsp;|&nbsp;";
		}
?>
	<a href="change_passwd_form.php">Change password</a>
	<br />
	<a href="recommend.php">Recommend URLs to me</a> &nbsp;|&nbsp;
	<a href="logout.php">Logout</a>
	<hr />
<?php
	}
?>

<?php 
	function display_add_bm_form() {
?>
		<form name="bm_table" action="add_bms.php" method="post">
		<table width="250" cellpadding="2" cellspacing="0" bgcolor="#cccccc">
		<tr><td>New BM:</td>
		<td><input type="text" name="new_url" value="http://"
			 size="30" maxlength="255"/></td></tr>
		<tr><td colspan="2" align="center">
			<input type="submit" value="Add bookmark"/></td></tr>
		</table>
		</form>
<?php
}
?>

<?php 
	function display_user_urls($url_array) {
	  // display the table of URLs

	  // set global variable, so we can test later if this is on the page
	  global $bm_table;
	  $bm_table = true;
?>
	  <br />
	  <form name="bm_table" action="delete_bms.php" method="post">
	  <table width="300" cellpadding="2" cellspacing="0">
	  <?php
	  $color = "#cccccc";
	  echo "<tr bgcolor=\"".$color."\"><td><strong>Bookmark</strong></td>";
	  echo "<td><strong>Delete?</strong></td></tr>";
	  if ((is_array($url_array)) && (count($url_array) > 0)) {
		foreach ($url_array as $url)  {
		  if ($color == "#cccccc") {
			$color = "#ffffff";
		  } else {
			$color = "#cccccc";
		  }
		  //remember to call htmlspecialchars() when we are displaying user data
		  echo "<tr bgcolor=\"".$color."\"><td><a href=\"".$url."\">".htmlspecialchars($url)."</a></td>
				<td><input type=\"checkbox\" name=\"del_me[]\"
					value=\"".$url."\"/></td>
				</tr>";
		}
	  } else {
		echo "<tr><td>No bookmarks on record</td></tr>";
	  }
?>
	  </table>
	  </form>
<?php
	}
?>