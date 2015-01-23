#include "VspdCTOMySQL.h"

VspdCTOMySQL::VspdCTOMySQL()
{

}

VspdCTOMySQL::~VspdCTOMySQL()
{

}

int VspdCTOMySQL::ConnMySQL(char *host , char *port , char *Db, char *user, char *password , char *charset , char *Msg)
{
	if(mysql_init(&mysql) == NULL)
	{
		Msg = " inital mysql handle error" ;
		return 1 ;
	}
	if(mysql_real_connect(&mysql,host,user,password,Db,0,NULL,0) == NULL)
	{
		Msg = "Failed to connect to database Error";
		return 1 ;
	}
	
	if(mysql_set_character_set(&mysql,"GBK") != 0)
	{
		Msg = "mgsql_set_character_set Error" ;
		return 1 ;
	}	
	return 0 ;
}
