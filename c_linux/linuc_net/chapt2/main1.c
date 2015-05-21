/*************************************************************************
	> File Name: main1.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Wed 20 May 2015 07:14:18 AM PDT
 ************************************************************************/

#include<stdio.h>
#include<dlfcn.h>

//动态加载链接库
// run >> gcc testdl main1.c libstrfun1.so -ldl

int main(void)
{

	char src[] = "hello Dymatic";
	int ( *pStrLenFun)(char *str);
	void *phandle = NULL ;
	char *perr = NULL;
	phandle = dlopen("./libstrfun1.so",RTLD_LAZY);

	if(!phandle)
	{
		printf("Failed load libaray!\n");
	}

	perr = dlerror();

	if(perr != NULL)
	{
		printf("%s\n",perr);
		return 0;
	}

	pStrLenFun = dlsym(phandle , "StrLen");
	perr = dlerror();
	if(perr != NULL)
	{
		printf("%s\n",perr);
		return 0;
	}

	printf("the string length is: %d\n",pStrLenFun(src));
	dlclose(phandle);
	return 0;
}

