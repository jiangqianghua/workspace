#########################################################################
# File Name: param2.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Fri 10 Jul 2015 11:08:11 PM PDT
#########################################################################
#!/bin/bash
echo $0
echo $*
while getopts :ab:c: opt
do
	case $opt in
		a)
			echo "a=$OPTARG";;
#			echo $OPTIND;;
		b)
			echo "b=$OPTARG";;
#			echo "$OPTIND";;
		c)
			echo "c=$OPTARG";;
#			echo "$OPTIND";;
		d) 
			echo "d=$OPTARG";;
		e)
			echo "e=$OPTARG";;
		f)
			echo "f=$OPTARG";;
		?)
			echo "error";;
#			exit 1;;
		esac
done
#echo $OPTIND
echo $*
echo $0


