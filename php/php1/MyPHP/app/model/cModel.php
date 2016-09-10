<?php 
namespace app\model;

use core\lib\model ;

class cModel extends model
{

	public $table = 'Person'; 
	public function lists()
	{
		$ret = $this->select($this->table,"*");
		return $ret ;
	}

	public function getOne($id)
	{
		$ret = $this->get($this->table,"*",array(
			"FirstName"=>$id));
		return $ret ;
	}

	public function setOne($id , $data)
	{
		return $this->update($this->data,$data,array(
			"FirstName"=>$id
			));
	}

	public function delOne($id)
	{
		return $this->delete($this->table , array(
			"FirstName"=>$id
			));
	}
}