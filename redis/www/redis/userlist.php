<a href='adduser.php'>ע��</a>
<?php
	require("redis.php");
	for($i = 1 ; $i <= ($redis->get("userid")) ; $i++){
		$data[] = $redis->hgetall("user:".$i);
	}
	//var_dump($data)
	$data = array_filter($data); //���˿�Ԫ��
?>


<table border = 1>
	<tr>
		<th>uid</th>
		<th>username</th>
		<th>age</th>
		<th>����</th>
	</tr>
<?php foreach($data as $v){?>
	<tr>
		<td> <?php echo $v['uid']?></td>
		<td> <?php echo $v['username']?></td>
		<td> <?php echo $v['age']?></td>
		<td> <a href="del.php?id=<?php echo $v['uid'] ?>">ɾ��</td>
		<td> <a href="mod.php?id=<?php echo $v['uid'] ?>">�༭</td>
	</tr>
<?php }?>
</table> 