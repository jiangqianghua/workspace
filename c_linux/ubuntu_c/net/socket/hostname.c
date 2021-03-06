/*************************************************************************
	> File Name: hostname.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sat 20 Dec 2014 05:40:42 AM PST
 ************************************************************************/

#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<string.h>
#include<unistd.h>
#include<netdb.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<sys/types.h>
#include<arpa/inet.h>

int main(int argc , char **argv)
{
	struct sockaddr_in addr ; 
	struct hostnet *host ; 
	char **alias ;
	if(argc < 2)
	{
		fprintf(stderr , "Usage:%s hostname|i[..\n\a" ,argv[0]) ;
		exit(1);
	}

	argv++ ;

	for(; *argv != NULL; argv++)
	{
		if(inet_aton(*argv , &addr.sin_addr) != 0 )
		{
			host = gethostbyname(*argv) ;
			printf("Address information of host %s\n" ,*argv) ;
		}
		else
		{
			host = gethostbyname(*argv) ; 
			printf("Address information of host %s\n" , *argv) ;
		}

		if(host == NULL)
		{
			fprintf(stderr , "No address information of %s\n" , *argv) ;
			continue ;
		}

//		printf("Official host name %s \n" , host->h_name) ;
//		printf("Name aliases:") ;
//		for(alias = host->h_addr_list ; *alias != NULL ; alias++)
//		{
//'			printf("%s ," , inet_ntoa(*(struct in_addr *)(*alias))) ; 
//		}
	}

}
