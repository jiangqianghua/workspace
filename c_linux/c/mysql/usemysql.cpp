#include <mysql/mysql.h>
#include <iostream>
#include <string>
using namespace std ;
//g++ $(mysql_config --cflags) usemysql.cpp -o usemysql $(mysql_config --libs)
MYSQL mysql ;
void insert()
{
	string sql = "insert into Person(FirstName , LastName ,Age) values('ruan' ,'lili' , 24 )";
	mysql_query(&mysql , sql.c_str());
	mysql_close(&mysql);
}

void update()
{
	string sql = "update Person set FirstName = 'mr ruan' where Age = 24 ";
	mysql_query(&mysql , sql.c_str());
	mysql_close(&mysql);
}

void show()
{
	string sql = "select * from Person";
	mysql_query(&mysql , sql.c_str());
	MYSQL_RES *result = NULL;
	MYSQL_FIELD *field = NULL;
	result = mysql_store_result(&mysql);
	int rowcount = mysql_num_rows(result);
	cout<< rowcount<< endl ;
	int fieldCount = mysql_num_fields(result);
	for ( int i = 0 ; i < fieldCount ; i++)
	{
		field = mysql_fetch_field_direct(result , i);
		cout << field->name<<"\t\t" ;
	}
	cout << endl ;
	MYSQL_ROW row = NULL;
	row = mysql_fetch_row(result);
	while(NULL != row)
	{
		for(int i = 0 ; i < fieldCount ; i++)
		{
			cout << row[i] << "\t\t" ;
		}
		cout << endl ;
		row = mysql_fetch_row(result);
	}
	mysql_close(&mysql);
}
int main()
{

	mysql_init(&mysql);	
	mysql_real_connect(&mysql , "localhost" , "root" , "150700" ,"phpdb" , 3306 ,NULL,0);
//	insert();
//	update();
	show();
}
