#########################################################################
# File Name: if1.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Thu 03 Sep 2015 08:28:17 PM PDT
#########################################################################
#!/bin/bash

#test1=$(env | grep USERNAME | cut -d "=" -f 2)
#echo $test1
test1=$(whoami)
if [ "$test1" == "root" ]
then
	echo "current user is root"
fi
