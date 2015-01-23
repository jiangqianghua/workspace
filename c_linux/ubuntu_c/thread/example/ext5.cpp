#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>

#define MAXARG 10
#define LINSIZ 80
#define CMDSIZ 8

extern char **environ ;
char *quit = "quit.quit" ;
char cmdbuf[CMDSIZE][LINSZI];
int cmdflag[CMDSIZ];

int main()
{
	int i ; 
	for(;;)
	{
		printf("mini_SH-->");
		for( i = 7 ; i >= 0 ; i-- )
		{
			cmdflag[i] = 0 ;
			cmdbuf[i][0] = '\0' ;
		}
		if(i = readcmd())
			docommand(i);
		else
			printf("read command faild , try again!!!\n");
	}

}

int readcmd()
{
	char c, *p ; 
	int i = 0 ; 
	p = cmdbuf[0] ; 
	while((c = getchar()) != '\n')
	{
		if( c == ';')
		{
			*p = '\0' ;
			if(++i == 6)
				return (++i) ;
			p = cmdbuf[i] ;
		}
		else if( c == '&')
		{
			cmdflag[i] = 1;
		}
		else
			*p++ = c ;
	}
	*p = '\0';
	return(++i) ; 
}	


void docommand(int i)
{

	int j , stat , pid ;
	char *argl[MAXARG] ,args[LINSIZ] ;
	char c , *argsp , **arglp , *p ;
	
	for( j = 0 ; j < i ; j++ )
	{
		arglp = argl ; 
		argsp = args ;
		p = cmdbuf[j] ; 
		while((c = *p++) != '\0')
		{
			while(c == '' || c == '\t')
			{
				c = *p++ ;
			}
			
			if(c == '\0')
			{
				*argsp++ = '\0' ;
				break ;
			}

			*arglp++ = argsp ; 
			
		}
	}
}



