<?php 
	function do_html_header($title = '') {
  // print an HTML header

  // declare the session variables we want access to inside the function
  if (!$_SESSION['items']) {
    $_SESSION['items'] = '0';
  }
  if (!$_SESSION['total_price']) {
    $_SESSION['total_price'] = '0.00';
  }
?>
  <html>
  <head>
    <title><?php echo $title; ?></title>
    <style>
      h2 { font-family: Arial, Helvetica, sans-serif; font-size: 22px; color: red; margin: 6px }
      body { font-family: Arial, Helvetica, sans-serif; font-size: 13px }
      li, td { font-family: Arial, Helvetica, sans-serif; font-size: 13px }
      hr { color: #FF0000; width=70%; text-align=center}
      a { color: #000000 }
    </style>
  </head>
  <body>
  <table width="100%" border="0" cellspacing="0" bgcolor="#cccccc">
  <tr>
  <td rowspan="2">
  <a href="index.php"><img src="images/Book-O-Rama.gif" alt="Bookorama" border="0"
       align="left" valign="bottom" height="55" width="325"/></a>
  </td>
  <td align="right" valign="bottom">
  <?php
     if(isset($_SESSION['admin_user'])) {
       echo "&nbsp;";
     } else {
       echo "Total Items = ".$_SESSION['items'];
     }
  ?>
  </td>
  <td align="right" rowspan="2" width="135">
  <?php
     if(isset($_SESSION['admin_user'])) {
       display_button('logout.php', 'log-out', 'Log Out');
     } else {
       display_button('show_cart.php', 'view-cart', 'View Your Shopping Cart');
     }
  ?>
  </tr>
  <tr>
  <td align="right" valign="top">
  <?php
     if(isset($_SESSION['admin_user'])) {
       echo "&nbsp;";
     } else {
       echo "Total Price = $".number_format($_SESSION['total_price'],2);
     }
  ?>
  </td>
  </tr>
  </table>
<?php
  if($title) {
    do_html_heading($title);
  }
}
?>

<?php 
function do_html_footer() {
  // print an HTML footer
?>
  </body>
  </html>
<?php
}
?>

<?php 
function do_html_heading($heading) {
  // print heading
?>
  <h2><?php echo $heading; ?></h2>
<?php
}
?>

<?php 
	
	function display_categories($cat_array)
	{
		if(!is_array($cat_array))
		{
			echo "<p>No categroies currently available</p>";
			return ;
		}
		echo "<ul>";
		
		foreach($cat_array as $row)
		{
			$url ="show_cat.php?catid=".($row['catid']);
			$title = $row['catname'];
			echo '<li>';
			do_html_url($url , $title);
			echo "</li>";
		}
		echo "</ul>";
		echo "<hr />";
	}

	function display_books($book_array)
	{
	  //display all books in the array passed in
	  if (!is_array($book_array)) {
		echo "<p>No books currently available in this category</p>";
	  } else {
		//create table
		echo "<table width=\"100%\" border=\"0\">";

		//create a table row for each book
		foreach ($book_array as $row) {
		  $url = "show_book.php?isbn=".$row['isbn'];
		  echo "<tr><td>";
		  if (@file_exists("images/".$row['isbn'].".jpg")) {
			$title = "<img src=\"images/".$row['isbn'].".jpg\"
					  style=\"border: 1px solid black\"/>";
			do_html_url($url, $title);
		  } else {
			echo "&nbsp;";
		  }
		  echo "</td><td>";
		  $title = $row['title']." by ".$row['author'];
		  do_html_url($url, $title);
		  echo "</td></tr>";
		}

		echo "</table>";
	  }
  }

  echo "<hr />";
	
	
	function do_html_url($url , $title)
	{
?>
		<a href="<?php echo $url;?>"><?php echo $title;?></a><br />
<?php
	}
	
	function display_button($target, $image, $alt)
	{
	  echo "<div align=\"center\"><a href=\"".$target."\">
			  <img src=\"images/".$image.".gif\"
			   alt=\"".$alt."\" border=\"0\" height=\"50\"
			   width=\"135\"/></a></div>";
	}
	
	
	function display_book_details($book) {
	  // display all details about this book
	  if (is_array($book)) 
	  {
		echo "<table><tr>";
		//display the picture if there is one
		if (@file_exists("images/".$book['isbn'].".jpg"))  {
		  $size = GetImageSize("images/".$book['isbn'].".jpg");
		  if(($size[0] > 0) && ($size[1] > 0)) {
			echo "<td><img src=\"images/".$book['isbn'].".jpg\"
				  style=\"border: 1px solid black\"/></td>";
		  }
		}
		echo "<td><ul>";
		echo "<li><strong>Author:</strong> ";
		echo $book['author'];
		echo "</li><li><strong>ISBN:</strong> ";
		echo $book['isbn'];
		echo "</li><li><strong>Our Price:</strong> ";
		echo number_format($book['price'], 2);
		echo "</li><li><strong>Description:</strong> ";
		echo $book['description'];
		echo "</li></ul></td></tr></table>";
	  } else {
		echo "<p>The details of this book cannot be displayed at this time.</p>";
	  }
	  echo "<hr />";
	}
?>