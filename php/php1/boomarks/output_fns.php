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