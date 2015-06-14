/*************************************************************************
	> File Name: shttpd.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Thu 11 Jun 2015 08:38:24 AM PDT
 ************************************************************************/

#include "shttpd.h"

struct conf_opts conf_para = 
{
	"/usr/local/var/www/cgi-bin",
	"index.html",
	"/usr/local/var/www/",
	8080,
	4,
	3,
	2
};

static void sig_int(int num)
{
//	Worker_ScheduleStop();

	return ;
}
int main(int argc , char *argv[])
{

	signal(SIGINT , sig_int);

	Para_Init(argc ,argv) ;

//	int s = do_listen();

//	Worker_ScheduleRun(s);

	return 0 ;
}
