/*************************************************************************
	> File Name: string.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Wed 20 May 2015 05:51:58 AM PDT
 ************************************************************************/

#define ENDSTRING '\0'
int StrLen(char *string)
{
	int len = 0 ;

	while(*string++ != ENDSTRING)
		len++;
	return len;
}

