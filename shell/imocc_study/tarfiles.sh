#########################################################################
# File Name: lsfile_for.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Fri 04 Sep 2015 12:30:28 AM PDT
#########################################################################
#!/bin/bash
   
cd testdir
ls *.tar.gz > ls.log  #override
ls *.tgz >> ls.log   #append

for i in $(cat ls.log)
do
	tar -zxf $i & > /dev/null
done

rm -rf ls.log
