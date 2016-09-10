<?php 
namespace core\lib ;

use core\lib\config ;
class log
{
	/**
	1  确定日志存储方式


	2 写日志
	*/
	static $class ;
	static public function init()
	{
		// 确定储存方式
		$drive = config::get('DRIVE','log');
		$class = '\core\lib\drive\log\\'.$drive ;
		p($class);
		self::$class = new $class();
	}

	static public function log($name,$file='Log')
	{
		self::$class->log($name,$file);
	}
}