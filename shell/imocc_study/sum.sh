#########################################################################
# File Name: sum.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Thu 03 Sep 2015 10:23:17 PM PDT
#########################################################################
#!/bin/bash
read -t 30 -p "please input num1:" num1
read -t 30 -p "please input num2:" num2
read -t 30 -p "please input a operator:" ope

if [ -z "$num1" -o -z "$num2" -o -z "$ope" ]
then
	echo input error
	exit 1
fi

test1=$(echo $num1 | sed 's/[0-9]//g')
test2=$(echo $num2 | sed 's/[0-9]//g')

if [ -n "$test1" -o -n "$test2" ]
then
	echo the num error
	exit 2
fi

if [ "$ope" == '+' ]
then
	sum=$(($num1 + $num2))
elif [ "$ope" == '-' ]
then
	sum=$(($num1 - $num2))
elif [ "$ope" = '*' ]
then
	sum=$(($num1 * $num2))
elif [ "$ope" == "/" ]
then
	sum=$(($num1/$num2))
else
	echo "operator input error"
	exit 3
fi

echo "$num1 $ope $num2 :$sum "

