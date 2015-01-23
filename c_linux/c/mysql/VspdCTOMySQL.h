#include <stdio.h>
#include <string>
#include <mysql/mysql.h>
using namespace std  ;

class VspdCTOMySQL
{
public:
	MYSQL mysql ;
	VspdCTOMySQL();
	~VspdCTOMySQL();
	
	int ConnMySQL(char *host , char *port , char *Db , char *user , char *password , char *charset , char *Msg);	
}
