<?php 
namespace app\ctrl;
use core\lib\model;

class indexCtrl extends \core\immoc
{
	public function index()
	{
		//p('this is index...');
		
		//$model = new \core\lib\model();
		//$sql = "select * from Person";
		//$ret = $model->query($sql);
		//p($ret->fetchAll());
		
		
		$data = 'hello world';
		$title = 'views - model';
		// 视图
		$this->assign('data',$data);
		$this->assign('title',$title);
		$this->display('index.html');

		//  测试配置文件
		//get  config
		//$confClass = '\core\lib\config';
		//$temp = $confClass::get('CTRL','route');
		//p($temp);
		//$temp = $confClass::get('ACTION','route');
		//p($temp);


		//  测试数据库，medoo数据

/**
		$model = new model();
		dump($model);
		$data = $model->select('Person',"*");
		dump($data);

		// insert
		$setData = array(
			'name'
			);

			**/

/**
		$model = new \app\model\cModel();
		$ret = $model->lists();
		dump($ret);

		$ret1 = $model->getOne("jiang1");
		dump($ret1);
		**/


	}
}
