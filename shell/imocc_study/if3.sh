#########################################################################
# File Name: if3.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Thu 03 Sep 2015 09:04:37 PM PDT
#########################################################################
#!/bin/bash
read -t 30 -p "please input a dir:" dir

if [ -d "$dir" ]
then
	echo "is dir"
else
	echo no dir
fi
