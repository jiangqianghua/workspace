#########################################################################
# File Name: case.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Fri 04 Sep 2015 12:17:38 AM PDT
#########################################################################
#!/bin/bash
read -t 30 -p "please input yes/no:" cho

case "$cho" in
	"yes")
		echo "you choose yes"
		;;
	"no")
		echo "you choose no"
		;;
	*)
		echo "please input right"
		;;
esac
