<?php

/**
* 入口文件
*1. 自定义常量
*2.	加载函数库
*3. 启动框架
*/
//http://192.168.1.105/php1/MyPHP/
//https://github.com/moling3650/MFramework_php
define('IMMOC',realpath('./'));
define('CORE',IMMOC.'/core');
define('APP',IMMOC.'/app');
define('LOATION','/php1/MyPHP/');
define('INDEX','/index.php');
define('MODULE','app');
define('DEBUG', true);

include "vendor/autoload.php";

if(DEBUG){
	$whoops = new \Whoops\Run;
    $whoops->pushHandler(new \Whoops\Handler\PrettyPageHandler);
    $whoops->register();
	ini_set('display_error', 'On');
}else{
	ini_set('display_error', 'Off');
}

//dump($_SERVER);exit();
include CORE.'/common/function.php';

include CORE.'/immoc.php';

spl_autoload_register('\core\immoc::load');
\core\immoc::run();
