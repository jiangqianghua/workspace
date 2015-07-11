#########################################################################
# File Name: param1.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Thu 09 Jul 2015 09:26:21 AM PDT
#########################################################################
#!/bin/bash
if(($# < 1 ))
then
	echo "error... need args"
	exit 1
fi

echo "command is ",$0
echo "params is $@"
echo "params is $*"
echo "args are:"

for arg in $@
do
	echo $arg
done
