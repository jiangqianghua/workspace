# 1 "string.c"
# 1 "<command-line>"
# 1 "string.c"
# 9 "string.c"
int StrLen(char *string)
{
 int len = 0 ;

 while(*string++ != '\0')
  len++;
 return len;
}
