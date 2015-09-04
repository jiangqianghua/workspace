#########################################################################
# File Name: if2.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Thu 03 Sep 2015 08:51:47 PM PDT
#########################################################################
#!/bin/bash
test1=$(df -h | grep sda1 | awk '{print $5}' | cut -d '%' -f 1)
#echo $test1

if [ "$test1" -ge "60" ]
then
	echo "the sda is full"
fi
