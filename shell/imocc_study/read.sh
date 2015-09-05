#########################################################################
# File Name: read.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Fri 04 Sep 2015 05:16:44 AM PDT
#########################################################################
#!/bin/bash
read -p "please input your name:" -t 30 name

echo $name

read -p "please input your password:" -s passwd
echo -e "\n"
echo $passwd

read -p "please input you sex [M/F]:" -n 1 sex
echo -e "\n"
echo $sex
