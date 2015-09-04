#########################################################################
# File Name: for2.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Fri 04 Sep 2015 12:47:43 AM PDT
#########################################################################
#!/bin/bash
s=0 
for((i = 1 ; i<=100 ; i=i+1))
do
	s=$(($s+$i))
done

echo $s
