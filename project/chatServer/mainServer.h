/*************************************************************************
	> File Name: mainServer.h
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 16 May 2015 07:18:45 PM PDT
 ************************************************************************/

#include <stdio.h>

int port ;
int sockMax = 128;
int count = 0 ;
int startServer();
void *startClientChatThread(void *arg);
