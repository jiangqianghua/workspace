/*************************************************************************
	> File Name: shttpd_parameters.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 13 Jun 2015 01:18:20 AM PDT
 ************************************************************************/

#include "shttpd.h"

static char *l_opt_arg;

static char *shortopts = "c:d:f:ho:l:m:t:";

extern struct conf_opts conf_para ;
static struct option longopts[] = 
{
	{"CGIRoot" , required_argument , NULL,'c'},
	{"ConfigFile",required_argument , NULL , 'f'},
	{"DefaultFile",required_argument, NULL ,'d'},
	{"DocumentRoot" , required_argument ,NULL , 'o'},
	{"ListenPort" , required_argument , NULL , 'l'},
	{"MaxClient" ,required_argument , NULL, 'm'},
	{"TimeOut", required_argument,NULL, 't'},
	{"Help",required_argument , NULL, 'h'},
	{0,0,0,0},
};

static void display_para()
{
	printf("shttpd listenport:%d\n",conf_para.ListenPort);
	printf("maxClient:%d\n",conf_para.MaxClient);
	printf("DocumentRoot:%s\n",conf_para.DocumentRoot);
	printf("DefaultFile:%s\n",conf_para.DefaultFile);
	printf("CGIRoot:%s\n",conf_para.CGIRoot);
	printf("TimeOut:%d\n",conf_para.TimeOut);
	printf("ConfigFile:%s\n",conf_para.ConfigFile);
}

static void display_usage(void)
{
	printf("show usage\n");
}
static int Para_CmdParse(int argc , char *argv[])
{
	int c ;
	int len ; 
	int value ;
	while((c = getopt_long(argc , argv , shortopts , longopts ,NULL)) != -1)
	{
		switch(c)
		{
			case 'c':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0]!= ':')
				{

					len = strlen(l_opt_arg);
					memcpy(conf_para.CGIRoot,l_opt_arg, len+1);
				}
				break;
			case 'd':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0]!= ':')
				{
					len = strlen(l_opt_arg);
					memcpy(conf_para.DefaultFile,l_opt_arg, len+1);
				}
				break;
			case 'f':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0]!= ':')
				{
					len = strlen(l_opt_arg);
					memcpy(conf_para.ConfigFile,l_opt_arg, len+1);
				}
				break;
			case 'o':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0]!= ':')
				{
					len = strlen(l_opt_arg);
					memcpy(conf_para.DocumentRoot,l_opt_arg, len+1);
				}
				break;
			case 'l':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0]!= ':')
				{
				
					len = strlen(l_opt_arg);
					value = strtol(l_opt_arg,NULL,10);
					if(value != LONG_MAX && value != LONG_MIN)
						conf_para.ListenPort = value;
				}
				break;
			case 'm':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0]!= ':')
				{
					len = strlen(l_opt_arg);
					value = strtol(l_opt_arg,NULL,10);
					if(value != LONG_MAX && value != LONG_MIN)
				
						conf_para.MaxClient = value;
				}
				break;

			case 't':
				l_opt_arg = optarg;
				if(l_opt_arg && l_opt_arg[0]!= ':')
				{
					len = strlen(l_opt_arg);
					value = strtol(l_opt_arg,NULL,10);
					if(value != LONG_MAX && value != LONG_MIN)
				
						conf_para.TimeOut = value;
				}
				break;

			case '?':
				printf("Invalid para\n");
			case 'h':
				display_usage();
				break;
		}
	}
	return 0;
}
static int conf_readline(int fd , char *buff , int len)
{
	int n = -1 ; 
	int i = 0 ;
	int begin = 0 ;

	memset(buff , 0, len);
	for(i = 0 ; i < len ; i++)
	{
		n = read(fd , buff+1 , 1);	
		if( n == 0)
		{
			*(buff+i) = '\0';
			break ;
		}
		else if(*(buff+i)=='\r' || *(buff+i)=='\n')
		{
			if(begin)
			{
				*(buff+i) = '\0';
				break;
			}
		}
		else
		{
			begin = 1 ;
		}
	}
	return i ;
}
void Para_FileParse(char *file)
{
	#define LINELENGTH 256
	char line[LINELENGTH];
	char *name = NULL ;
	char *value = NULL ;
	int fd = -1 ; 
	int n = 0 ; 

	fd = open(file , O_RDONLY);
	if(fd == -1)
	{
		printf(" open shttpd config error\n");
		return ;
	}

	while((n = conf_readline(fd , line , LINELENGTH)) != 0)
	{	
		char *pos = line ; 
		while(isspace(*pos))
		{
			pos++;
		}

		if(*pos == '#')
		{
			continue ;
		}
		name = pos ; 
		while(!isspace(*pos)&&*pos != '=')
		{
			pos++;
		}
		*pos = '\0';

		while(isspace(*pos))
		{
			pos++;
		}
		value = pos ;
		while(!isspace(*pos) && *pos != '\r' && *pos != '\n')
		{
			pos++;
		}

		int ivalue ;
		//..........................

	}

}
void Para_Init(int argc , char *argv[])
{
	Para_CmdParse(argc , argv);
//	if(strlen(conf_para.ConfigFile))
//		Para_FileParse(conf_para.ConfigFile);

	display_para();

	return ;
}
