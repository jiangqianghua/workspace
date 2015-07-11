#########################################################################
# File Name: param.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Thu 09 Jul 2015 08:15:25 AM PDT
#########################################################################
#!/bin/bash
echo 'the number param num is',$# # the number of params
for((i=0;i<$#;i++))
do
	echo $i
done

echo '-------------------------------'
((j=0))
while((j<$#))
do
	echo $j
	((j=j+1))
done
