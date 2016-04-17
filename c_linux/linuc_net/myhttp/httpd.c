#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <ctype.h>
#include <strings.h>
#include <string.h>
#include <sys/stat.h>
#include <pthread.h>
#include <sys/wait.h>
#include <stdlib.h>

#define ISspace(x) isspace((int)(x))
#define SERVER_STRING "Server: jdbhttpd/0.1.0\r\n"
// run >> gcc -W -Wall -pthread -o httpd httpd.c

void not_found(int client)
{
 char buf[1024];

 sprintf(buf, "HTTP/1.0 404 NOT FOUND\r\n");
 send(client, buf, strlen(buf), 0);
 sprintf(buf, SERVER_STRING);
 send(client, buf, strlen(buf), 0);
 sprintf(buf, "Content-Type: text/html\r\n");
 send(client, buf, strlen(buf), 0);
 sprintf(buf, "\r\n");
 send(client, buf, strlen(buf), 0);
 sprintf(buf, "<HTML><TITLE>Not Found</TITLE>\r\n");
 send(client, buf, strlen(buf), 0);
 sprintf(buf, "<BODY><P>The server could not fulfill\r\n");
 send(client, buf, strlen(buf), 0);
 sprintf(buf, "your request because the resource specified\r\n");
 send(client, buf, strlen(buf), 0);
 sprintf(buf, "is unavailable or nonexistent.\r\n");
 send(client, buf, strlen(buf), 0);
 sprintf(buf, "</BODY></HTML>\r\n");
 send(client, buf, strlen(buf), 0);
}

void unimplemented(int client)
{
	char buf[1024];

	sprintf(buf , "HTTP/1.0 501 method Not Implement\r\n");
	send(client , buf , strlen(buf) , 0);
	sprintf(buf , SERVER_STRING);
	send(client , buf , strlen(buf) , 0);
	sprintf(buf , "Content-Type: text/html\r\n");
	send(client , buf , strlen(buf) , 0);
	sprintf(buf , "\r\n");
	send(client , buf , strlen(buf) , 0);
	sprintf(buf ,"<HTML><TITLE>Not Found</TITLE></HTML>\r\n");
	send(client , buf , strlen(buf) , 0);
	sprintf(buf , "<BODY><P>The server could not fulfill</P></BODY>\r\n");
	send(client , buf , strlen(buf) , 0);
	sprintf(buf , "your request because the resource specified\r\n");
 	send(client , buf, strlen(buf), 0);
 	sprintf(buf , "is unavailable or nonexistent.\r\n");
 	send(client , buf, strlen(buf), 0);
 	sprintf(buf , "</BODY></HTML>\r\n");
 	send(client , buf, strlen(buf), 0);
}

int get_line(int sock , char *buf , int size)
{
	int i = 0 ; 
	char c = '\0';
	int n;

	while((i < size - 1) && (c != '\n'))
	{
		n = recv(sock , &c , 1 , 0);
		printf("%02x\n",c);
		if(n > 0)
		{
			if(c == 'r')
			{
				n = recv(sock , &c , 1 , MSG_PEEK);
				if((n > 0) && (c == '\n'))
					recv(sock , &c , 1 , 0);
				else
					c = '\n';
			}
			buf[i] = c ; 
			i++;
		}
		else
			c = '\n';
	}
	buf[i] = '\0';

	return (i);
}

void headers(int client, const char *filename)
{
 char buf[1024];
 (void)filename;  /* could use filename to determine file type */

 strcpy(buf, "HTTP/1.0 200 OK\r\n");
 send(client, buf, strlen(buf), 0);
 strcpy(buf, SERVER_STRING);
 send(client, buf, strlen(buf), 0);
 sprintf(buf, "Content-Type: text/html\r\n");
 send(client, buf, strlen(buf), 0);
 strcpy(buf, "\r\n");
 send(client, buf, strlen(buf), 0);
}

void cat(int client , FILE *resource)
{
	printf("cat\n");
	char buf[1024];
	fgets(buf , sizeof(buf) , resource);
	while(!feof(resource))
	{
		send(client , buf , strlen(buf) , 0);
		fgets(buf , sizeof(buf) , resource);
	}
}

/**
*  发送服务器文件给客户端
*/
void serve_file(int client , const char *filename)
{
	printf("serve_file \n");
	FILE *resource = NULL;
	int numchars = 1; 
	char buf[1024];

	buf[0] = 'A' ; buf[1] = '\0';
	
	while((numchars>0) && strcmp("\n" , buf))
	{
		// 读完剩下的客户端信息
		numchars = get_line(client , buf , sizeof(buf));
		printf("%s\n",buf );
	}

	printf("filename %s \n" , filename);
	resource = fopen(filename , "r");
	if(resource == NULL)
		not_found(client);
	else
	{
		headers(client,filename);
		cat(client , resource);
	}
	fclose(resource);
}

/**
* 接受客户端请求数据
*/
void accept_request(int client)
{
	char buf[1024];
	int numchars ;
	char method[255];
	char url[255];
	char path[512];
	size_t i , j ;
	struct stat st ; 
	int cgi = 0 ;

	char *query_string = NULL;
	numchars = get_line(client , buf , sizeof(buf));
	printf("quest:%d %s\n" , numchars , buf);
	//quest:16 GET / HTTP/1.1

	i = 0 ; j = 0 ;
	while(!ISspace(buf[j]) && (i < sizeof(method) -1 ))
	{
		// 获取请求方式
		method[i] = buf[j];
		i++ ; j++;
	}
	method[i] = '\0';
	printf("method %s\n",  method); // get method type

	if(strcasecmp(method , "GET") && strcasecmp(method , "POST"))
	{
		unimplemented(client);
		return ;
	}

	if(strcasecmp(method , "POST") == 0)
	{
		cgi = 1 ;
	}
	printf("1. cgi %d\n" , cgi);
	i = 0 ;

	while(ISspace(buf[j]) && (j < sizeof(buf)))
		j++ ;

	while(!ISspace(buf[j]) && (i < sizeof(url)-1) && (j < sizeof(buf)))
	{
		url[i] = buf[j] ;
		i++ ; 
		j++ ;
	}
	url[i] = '\0';
	printf("url %s\n", url); // print  /

	if(strcasecmp(method,"GET") == 0)
	{
		query_string = url ;
		while((*query_string != '?')&& (*query_string != '\0'))
		{
			query_string++ ;
		}
		if(*query_string == '?')
		{
			// 请求cgi
		}
	}

	sprintf(path , "htdocs%s" ,url);
	if(path[strlen(path) - 1] == '/')
	{
		strcat(path , "index.html");
		printf("path %s\n" , path);
	}
	if(stat(path , &st) == -1)  //查找文件是否成功
	{
		printf("st is error\n");
		while((numchars>0) && strcmp("\n",buf))
		{
			numchars = get_line(client , buf , sizeof(buf));
		}
		 not_found(client);
	}
	else
	{
		printf("st is ok\n");
		printf("cgi %d\n" , cgi);
		if ((st.st_mode & S_IFMT) == S_IFDIR)
   			strcat(path, "/index.html");
  		if ((st.st_mode & S_IXUSR) ||
      		(st.st_mode & S_IXGRP) ||
      		(st.st_mode & S_IXOTH) )
   			cgi = 1;
		if(!cgi)
		{
			printf("serve_file\n");
			serve_file(client , path);
		}
		else
		{
			printf("execute_cgi\n");
			//execute_cgi(client , path , method , query_string);
		}

		close(client);
	}
}
/**
* 打印错误信息，终止程序
*/
void error_die(const char *sc)
{
	perror(sc);
	exit(1);
}
/**
* 启动服务器监听
* 参数： 端口号，如果端口号传入为0 ，动态分配
* 返回值：socket
*/
int startup(u_short *port)
{
	//服务socket
	int httpd = 0 ;
	// socket结构体对象
	struct sockaddr_in name ;
	// 初始化服务器
	httpd = socket(PF_INET , SOCK_STREAM, 0);
	if(httpd == -1)
		error_die("socket error");
	// 初始化结构体对象
	memset(&name , 0 , sizeof(name));
	name.sin_family = AF_INET ; 
	name.sin_port = htons(*port);
	name.sin_addr.s_addr = htonl(INADDR_ANY);
	if(bind(httpd , (struct sockaddr *)&name , sizeof(name)) < 0)
		error_die("bind error");
	if(*port == 0)
	{
		int namelen = sizeof(name);
		if(getsockname(httpd,(struct  sockaddr *)&name , &namelen) == -1)
			error_die("getsock port error");
		*port = ntohs(name.sin_port);
	}
	// 开始监听端口
	if(listen(httpd , 5) < 0)
		error_die("listen error");
	return (httpd);
}
int main(void)
{
	// 服务端socket
	int server_sock = -1 ;
	// 端口号
	u_short port = 0 ;
	// 客户端socket请求
	int client_sock = -1 ;
	// 客户端socket结构体信息
	struct sockaddr_in client_name ;
	// 客户端socket结构体信息长度
	int client_name_len = sizeof(client_name);
	// 有客户端请求，开辟一块线程进行处理
	pthread_t newthread;

	server_sock = startup(&port);
	printf("http running on port %d\n", port);


	while(1)
	{
		client_sock = accept(server_sock , (struct sockaddr *)&client_name,
			&client_name_len);
		if(client_sock == -1)
			error_die("accept error");
		printf("%d\n",client_sock);
		if(pthread_create(&newthread , NULL , accept_request , client_sock) != 0)
			perror("pthread_create error");
	}

	close(server_sock);
	return (0);
}