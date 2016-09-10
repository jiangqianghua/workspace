<?php 
namespace core\lib\drive\log ;
// 文件系统
use core\lib\config ;
class file
{
	public $path ;
	public function __construct()
	{
		p('file init');
		$conf = config::get('OPTION','log');
		$this->path = $conf['PATH'];
		//p($conf['PATH']);
		//p($this->path);

	}
	public function log($message,$file = 'Log')
	{
		//  文件是否存在
		//p($this->path);
		$this->path = $this->path.date('Ymd');
		p($this->path);
		if(!is_dir($this->path))
		{
			mkdir($this->path,0777,true);
		}
		return file_put_contents($this->path.'/'.date('YmdH').$file.'.php',date('Y-m-d H:i:s').' '.json_encode($message).PHP_EOL,FILE_APPEND);
		
	}
}