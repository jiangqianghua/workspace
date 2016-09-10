<?php 

namespace core\lib ; 

use core\lib\config ;
class route
{
	public $ctrl ; 
	public $action ;
	public function __construct()
	{
		//p($_SERVER['REQUEST_URI']);
		
		$this->ctrl = "index";
		$this->action = "index";
		if(isset($_SERVER['REQUEST_URI']) && $_SERVER['REQUEST_URI'] != LOATION && $_SERVER['REQUEST_URI'] != LOATION.'index.php')
		{
			// 替换掉文件地址和入口地址，转为空
			$path = $_SERVER['REQUEST_URI'];
			$path = str_replace(LOATION, '/', $path);
			$path = str_replace(INDEX, '', $path);
			//p($path);
			$patharr = explode('/',trim($path,'/'));
			//p($patharr);
			if(isset($patharr[0]))
			{
				$this->ctrl = $patharr[0];
				unset($patharr[0]);
			}

			if(isset($patharr[1]))
			{
				$this->action = $patharr[1];
				unset($patharr[1]);
			}
			else
			{
				$this->action = config::get('ACTION','route');
			}
			//p($patharr);
			// url 多余的部分转换成参数Get
			$count = count($patharr) + 2;
			//p($count);
			$i = 2;
			while($i < $count)
			{
				if(isset($patharr[$i+1])){
					$_GET[$patharr[$i]] = $patharr[$i + 1];
				}
				$i = $i + 2 ;
			}
			//p($_GET);
		}
		else
		{
			//p('defalut');
			$this->action = config::get('ACTION','route');
			$this->ctrl = config::get('CTRL','route');
		}
		
	}
}