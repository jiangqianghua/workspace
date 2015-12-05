#include <stdio.h>
#include <string.h>

char delims[] = ' ';
int main()
{
   FILE * pFile;
   char mystring [100];
   int i = 0 ;
   for(i = 0 ; i < 100 ; i++)
   {
		mystring[i] = '\0';
   }

   pFile = fopen ("vec.txt" , "r");
   if (pFile == NULL)
		perror ("Error opening file");
   else {
		while( fgets (mystring , 100 , pFile)!= NULL )
		{
			 
			 mystring[strlen(mystring)] = '\0';
			 printf (" %s  %d\n" ,mystring ,strlen(mystring));
			
			 char* token = strtok( mystring, delims);
				while( token != NULL )
				{
					/* While there are tokens in "string" */
					printf( "%s - ", token );
					/* Get next token: */
					token = strtok( NULL, delims);
				}
				 printf ("\n-------\n");
		}
		fclose (pFile);
	}
   return 0;
}