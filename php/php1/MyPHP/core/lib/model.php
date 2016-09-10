<?php
namespace core\lib;

use core\lib\config ;
class model extends \medoo 
{
	public function __construct()
	{
		$option= config::all('database');
		//p($option);
		parent::__construct($option);
		
	}
}