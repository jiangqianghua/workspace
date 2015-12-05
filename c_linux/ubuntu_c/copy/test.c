// 内存拷贝 strcpy 和 memcpy的区别
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct cust_str {
    int id ;
    char last_name[20] ;
    char first_name[15];
} CUSTREC;

int main(int argc , char *argv[])
{
	char *src_string =" this is the source string";
	char dest_string[50] ;
	char *dest_string1 = (char *) malloc (sizeof(char)*50);
	dest_string1[0] = 'a' ;
	CUSTREC src_cust ;
	CUSTREC dest_cust ;
	printf("Hello! I'm going to copy src_string into dest_string!\n");

    printf("Done! dest_string is: %s\n" ,
    strcpy(dest_string, src_string)) ;
    printf("Done! dest_string1 is: %s\n" ,
    strcpy(dest_string1+1, src_string)) ;

    printf("Done! dest_string1 - is: %s\n" ,dest_string1) ;

    // 结构体拷贝，memcpy
    src_cust.id = 1 ;
    strcpy(src_cust.last_name , "qianghua");
    strcpy(src_cust.first_name , "jiang");

    memcpy(&dest_cust , &src_cust , sizeof(CUSTREC));
     printf("Done! I just copied customer number # %d (%s %s). \n" ,
        dest_cust. id, dest_cust. first_name, dest_cust. last_name) ;

    return 0 ;
}