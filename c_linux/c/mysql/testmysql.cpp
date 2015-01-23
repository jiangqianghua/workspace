#include <mysql/mysql.h>
#include <iostream>
using namespace std ;
// run
// >> g++ $(mysql_config --cflags) testmysql.cpp -o testmysql $(mysql_config --libs)
int main()
{
	MYSQL mysql ;
	mysql_init(&mysql);
	cout<<"mysql is running"<<endl ;
	return 0;
	
}
