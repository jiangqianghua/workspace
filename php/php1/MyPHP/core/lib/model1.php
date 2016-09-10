<?php
namespace core\lib;

use core\lib\config ;
class model extends \PDO 
{
	public function __construct()
	{
		//$dsn = 'mysql:host=localhost;dbname=phpdb';
		//$username = 'root';
		//$passwd = '150700';
		$databses= config::all('database');
		try{
			parent::__construct($databses['DSN'],$databses['USERNAME'],$databses['PASSWD'],$options);
		}catch(\PDOException $e){
			p($e->getMessage());
		}
		
	}
}