<?php 

function p($var)
{
	if(is_bool($var)){
		var_dump($var);
	} else if(is_null($var)){
		var_dump(NULL);
	}else{
		echo "<pre style='postion:relative;z-index:1000;padding:10px;
		border-radius:5px;background:#5F5F5F;border:1px solid #aaa;font-size:14px;
		line-height:18px;opacity:0.9'>".print_r($var,true)."</pre>";
	}
}
