<?php 
	
	function get_categories()
	{
		$conn = db_connect();
		
		$query = "select catid , catname from categories";
		
		$result = $conn->query($query);
		
		if(!$result)
		{
			return false ;
		}
		
		$num_cats = @$result->num_rows ;
		if($num_cats == 0)
		{
			return false ;
		}
		$result = db_result_to_array($result);
		
		return $result ;
	}
	
	function get_category_name($catid)
	{
		$conn = db_connect();
		
		$query = "select title from categories where catid='".$catid."'";
		
		$result = $conn->query($query);
		
		if(!$result)
		{
			return false ;
		}
		$num_cats = @$result->num_rows ;
		if($num_cats == 0)
		{
			return false ; 
		}
		
		$row = $result->fetch_object();
		
		return $row->catname ;
	}
	
	function get_books($catid)
	{
		$conn = db_connect();
		
		$query = "select * from books where catid='".$catid."'";
		
		$result = $conn->query($query);
		
		if(!$result)
		{
			return false ;
		}
		
		$num_cats = @$result->num_rows ;
		if($num_cats == 0)
		{
			return false ;
		}
		$result = db_result_to_array($result);
		
		return $result ;
	}
	
		
	function get_book_details($isbn)
	{
	  if ((!$isbn) || ($isbn=='')) {
		 return false;
	  }
	  $conn = db_connect();
	  $query = "select * from books where isbn='".$isbn."'";
	  $result = @$conn->query($query);
	  if (!$result) {
		 return false;
	  }
	  $result = @$result->fetch_assoc();
	  return $result;
	}
	
	function check_admin_user()
	{
		if(isset($_SESSION['admin_user']))
		{
			return true ;
		}
		return false ;
	}
?>