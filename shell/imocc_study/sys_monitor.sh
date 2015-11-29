#########################################################################
# File Name: sys_monitor.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Wed 25 Nov 2015 06:18:30 AM PST
#########################################################################
#!/bin/bash
clear
if [[ $# -eq 0]]
then
	reset_terminal=$(tput sgr0)
	#check os type
	os=$(uname -o)
	#check os release version and name
	os_name=$(cat /etc/issue)
	#check architecture
	architecture=$(uname -m)
	#check kernel release
	kernelrelease=$(uname -r)
	#check hostname
	hostname=$(uname -n)
	#check iternal ip
	internameip=$(hostname -I)
	#check erternal ip
	externalip=$(curl -s http://ipecho.net/plain)
	#check dns
	dns=$(cat /etc/resolv.conf |grep -E "\nameserver[ ]+"|awk '{print $NF')
	#check if connected to internet or no
	ping -c 1 www.baidu.com
	#check logged in users
	who>/tmp/who
	rm -f /tmp/who
fi
