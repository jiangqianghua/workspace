<a href='adduser.php'>ע��</a>
<?php
	require("redis.php");
	if(!empty($_COOKIE['auth'])){
		$id = $redis->get("auth".$_COOKIE['auth']);
		$name = $redis->hget("user:".$id,"username");
?>
��ӭ <?php echo $name?>
<a href='logout.php'>ע��</a>
<?php
	}else{
?>
<a href='login.php'>��½</a>
<?php 
	}?>
<?php
	// �û�����
	$count = $redis->lsize("uid") ;
	//echo $count ;
	// ҳ��С
	$page_size = 3 ; 
	// ��ǰҳ��
	$page_num = (!empty($_GET['page']))?$_GET['page']:1;
	//ҳ����
	$page_count = ceil($count/$page_size);
	
	$ids = $redis->lrange("uid",($page_num -1)*$page_size,(($page_num-1)*$page_size+$page_size-1));
	//var_dump($ids);
	/**for($i = 1 ; $i <= ($redis->get("userid")) ; $i++){
		$data[] = $redis->hgetall("user:".$i);
	}
	*/
	foreach($ids as $v)
	{
		$data[] = $redis->hgetall("user:".$v);
	}
	//var_dump($data)
	//$data = array_filter($data); //���˿�Ԫ��
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
		<td> <a href="del.php?id=<?php echo $v['uid'] ?>">ɾ��</a>
		<a href="mod.php?id=<?php echo $v['uid'] ?>">�༭</a>
		<?php if(!empty($_COOKIE['auth']) && $id!=$v['uid']){?>
		 <a href="addfans.php?id=<?php echo $v['uid'] ?>&uid=<?php echo $id?>">��ӹ�ע</a>
		<?php }?>
		</td>
		 
	</tr>
<?php }?>

<tr>
	<td colspan="4">
		<a href="?page=<?php echo (($page_num - 1)<1?1:($page_num-1))?>">��һҳ</a>
		<a href="?page=<?php echo (($page_num + 1)>$page_count?$page_count:($page_num + 1)) ?>">��һҳ</a>
		<a href="?page=1">��ҳ</a>
		��ǰ<?php echo $page_num?>ҳ
		�ܹ�<?php echo $page_count?>ҳ
		�ܹ�<?php echo $count?> ���û�
	</td>
</tr>
</table> 

<table border=1>
	<caption>�ҹ�ע��˭:</caption>
	<?php 
		$data = $redis->smembers("user:".$id."flowing");
		foreach($data as $row){
			$row = $redis->hgetall("user:".$row);
	?>
	<tr>
		<td><?php echo $row['uid']?> </td>
		<td><?php echo $row['username']?> </td>
		<td><?php echo $row['age']?> </td>
	</tr>
	<?php
		}
	?>
	<tr></tr>
</table>

<table border=1>
	<caption>�ҵķ�˿:</caption>
	<?php 
		$data = $redis->smembers("user:".$id.":flowers");
		foreach($data as $row){
			$row = $redis->hgetall("user:".$row);
	?>
	<tr>
		<td><?php echo $row['uid']?> </td>
		<td><?php echo $row['username']?> </td>
		<td><?php echo $row['age']?> </td>
	</tr>
	<?php
		}
	?>
	<tr></tr>
</table>