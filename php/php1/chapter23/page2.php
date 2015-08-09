<?php

	session_start();
	
echo 'the content of $_SESSION[\'sess_var\'] is'.$_SESSION['sess_var'].'<br>';
unset($_SESSION['sess_var']);

?>
<a href='page2.php'> next page</a>
