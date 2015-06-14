/*************************************************************************
	> File Name: shttpd.h
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Thu 11 Jun 2015 08:42:46 AM PDT
 ************************************************************************/


#ifndef __SHTTPD_H__
#define __SHTTPD_H__

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <netinet/in.h> /* for sockaddr_in */
#include <netdb.h>      /* for hosten */
#include <pthread.h>
#include <arpa/inet.h>
#include <signal.h>
#include <errno.h>    /* we want to catch some of these after all */
#include <unistd.h>   /* protos for read , write , close , etc */
#include <dirent.h>   /* for MAXNAMLEN */
#include <limits.h>
#include <getopt.h>
#include <sys/types.h>
#include <fcntl.h>
#include <ctype.h>
#include <stddef.h>

enum[WORKER_INITED , WORKER_RUNNING , WORKER_DETACHING , WORKER_IDEL];

struct conf_opts
{
	char CGIRoot[128];
	char DefaultFile[128];
	char DocumentRoot[128];
	char ConfigFile[128];
	int ListenPort ; 
	int MaxClient ; 
	int TimeOut ; 
	int InitClient ; 
};
struct worker_conn ; 
struct worker_ctll ;
struct conn_request ;
struct conn_response ;
struct worker_request
{
	struct vec  req ; 
}

struct worker_conn
{
	#define K 1024 
	char dreq[16*K];
	char dres[16*K];

	int cs ; 
	int to ;
	struct conn_response con_res ; 
	struct conn_request con_req;

	struct worker_ctl *work ;
}
struct worker_opts
{
	pthread_t th ; 
	int flags ; 
	pthread_mutex_t mutex ; 
	struct worker_ctl *work ;
}
#endif /* __SHTTPD_H__ */

