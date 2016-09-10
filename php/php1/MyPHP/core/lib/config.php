<?php
namespace core\lib ;

class config
{
	static public $conf = array();
	static public function get($name , $file)
	{
		/**
		1  配置文件是否存在
		2  判断配置是否存在
		3  缓存配置
		*/
		
		//p(self::$conf);
		if(isset(self::$conf[$file]) && isset(self::$conf[$file][$name]))
		{
			//p('get cache');
			return self::$conf[$file][$name];
		}
		else
		{
			$path = IMMOC.'/core/config/'.$file.'.php';
			//p('get');
			if(is_file($path)){
				$conf = include $path ;
				if(isset($conf[$name])){
					self::$conf[$file] = $conf ;
					return $conf[$name];
				}
				else
				{
					throw new \Exception('not find config name '.$name);
				}
			}
			else
			{
				throw new \Exception('not find config file '.$path);
			}
		}

	}


	static public function all($file)
	{
		if(isset(self::$conf[$file]))
		{
			//p('get cache');
			return self::$conf[$file][$name];
		}
		else
		{
			$path = IMMOC.'/core/config/'.$file.'.php';
			//p('get');
			if(is_file($path)){
				$conf = include $path ;
				self::$conf[$file] = $conf ;
				return $conf ;
			}
			else
			{
				throw new \Exception('not find config file '.$path);
			}
		}
	}

}