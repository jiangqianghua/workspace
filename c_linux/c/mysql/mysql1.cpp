#include <stdio.h>
#include <string>
#include <iostream>
#include "VspdCTOMySQL.h"

using namespace std ;

int main()
{
	cout << "connect  mysql " << endl ;
	char *host = "192.168.193.128" ;
	char *port = "3306";
	char *user = "root";
	char *password = "150700" ;
	char *dbname = "phptest";
	char *charset = "GBK";
	char *Msg = "" ;
	VspdCTOMySQL *vmysql = new VspdCTOMySQL ;
	if(vmysql->ConnMySQL(host,port,dbname,user,pasword,charset,Msg) == 0)
		cout << "success" << endl ;	
	else
		cout << Msg << endl ;	
}
