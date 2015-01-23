#include<stdio.h>
#include<fcntl.h>
#include<unistd.h>
#include<stdlib.h>
#include<string.h>
#define LNAME 9 
#define PERMS 0666
#define DATAFILE "datafile" 
#define USERS 3

typedef struct user{
	int uid ;
	char login[LNAME+1];
} RECORD ;

char *user_name[] = {"jiang1","jiang2","jiang3"} ;

int main()
{
	
	int i , fd ;
	RECORD rec ;
	if((fd = open(DATAFILE,O_WRONLY|O_TRUNC|O_CREAT , PERMS)) == -1)
	{
		perror("open");
		return 1 ;
	}
	for ( i = USERS - 1 ; i >= 0 ; i--)
	{
		rec.uid = i ;
		strcpy(rec.login , user_name[i]) ;
		lseek(fd , (long)i*sizeof(RECORD),SEEK_SET) ;
		write(fd,(char *)&rec , sizeof(RECORD));
	}
	lseek(fd , 0L , SEEK_END );
	close(fd);
	

	return 0;
}
