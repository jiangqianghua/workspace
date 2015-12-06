#include <stdio.h>

/**
stdin
stdout
stderr
*/
int main()
{

	// 余下方式等价于printf()
	fprintf(stdout ,"please input the value a:\n");

	int a ; 
	//scanf("%d",&a);
	fscanf(stdin , "%d" , &a);
	if(a < 0){
		fprintf(stderr, "the value must >0 %d \n", a);
		return 1 ;
	}
	return 0;
}