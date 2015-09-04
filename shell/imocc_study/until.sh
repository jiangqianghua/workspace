#########################################################################
# File Name: while.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Fri 04 Sep 2015 01:06:07 AM PDT
#########################################################################
#!/bin/bash
 i=1
 s=0

 until [ $i -gt 100 ]
 do
	 s=$(($s+$i))
	 i=$(($i+1))
 done

 echo $s
