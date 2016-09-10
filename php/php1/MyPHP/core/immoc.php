<?php 

namespace core ;

class immoc
{
	public static $classMap = array();
	public $assign ;
	static public function run()
	{
		// 启动日志
		\core\lib\log::init();
		//\core\lib\log::log($_SERVER);
		//p("ok");
		$route = new \core\lib\route();
		//p($route);
		$ctrlClass = $route->ctrl ;
		$action = $route->action ;
		$ctrlfile = APP.'/ctrl/'.$ctrlClass.'Ctrl.php' ;
		//p($ctrlfile);
		$ctrlClass = '\\'.MODULE.'\ctrl\\'.$ctrlClass.'Ctrl';
		//p($ctrlClass);
		if(!is_file(ctrlfile)){
			include $ctrlfile ;
			$ctrl = new $ctrlClass();
			$ctrl->$action();
			#http://192.168.1.103/php1/MyPHP/index.php/index/index
			\core\lib\log::log('ctrl:'.$route->ctrl.'   '.' action:'.$action);
		}
		else
		{
			throw new \Exception('not find ctrl '.$ctrlfile);
		}
	}


	static public function load($class)
	{
		// 自动加载类库
		//  new \core\route();
		//$class = '\core\route';
		//IMMOC.'/core/route.php';
		//p($class);

		$class = str_replace('\\', '/', $class);
		if(isset($classMap[$class]))
		{
			return true ;
		}
		else
		{
			$file = IMMOC.'/'.$class.'.php' ;
			//p($file);
			if(is_file($file)){
				include $file;
				self::$classMap[$class] = $class;
			}else
			{
				return false ;
			}
		}
		
	}

	public function assign($name,$value)
	{
		$this->assign[$name] = $value ;
	}

	// 使用原始办法加载数据
	public function display1($file)
	{
		$file = APP.'/views/'.$file ;
		//p($file);
		extract($this->assign);
		include $file ;
		/**
		if(is_file($file))
		{
			include $file ;
		}
		**/
	}

	// 使用twig加载数据
	public function display($file)
	{

		\Twig_Autoloader::register();
		$loader = new \Twig_Loader_Filesystem(APP.'/views');
		$twig = new \Twig_Environment($loader,array(
			'cache' => IMMOC.'/log',
			'debug' => DEBUG
			));
		$template = $twig->loadTemplate($file);
		$template->display($this->assign?$this->assign:'');
	}
}