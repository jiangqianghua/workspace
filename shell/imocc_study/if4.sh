#########################################################################
# File Name: if4.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Thu 03 Sep 2015 09:38:25 PM PDT
#########################################################################
#!/bin/bash
test1=$(ps aux | grep apache2 | grep -v grep)
#echo $test1

if [ -n "$test1" ]
then
	echo "apache is run"
else
	echo "apache is stop ,and now start apache"
	/etc/init.d/apache2 start
fi
