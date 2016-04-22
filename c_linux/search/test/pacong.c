#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <string.h>
#include <gumbo.h>
#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

//how run 
//>g++ -o pacong pacong.c -lgumbo
// ./pacong

//创建一个list容器的实例LISTINT   
typedef vector<string> LISTSTR;   
int searhIndex = 0 ;
int const MAXLINKS = 100 ;
LISTSTR urlList ;

// char *  to string
string getStrByChars(char *chstr)
{
	string str = chstr ;
}

// string to char *
const char * getChStrByString1(string str)
{
	return str.c_str(); 
}

char * getChStrByString(string str)
{
	char* c;
	const int len = str.length();
	c = new char[len+1];
	strcpy(c,str.c_str());
	return c ;
}
int isURLLink(string str)
{	

	char *p=strstr(getChStrByString(str),getChStrByString("http://")); 
	char *p1=strstr(getChStrByString(str),getChStrByString("https://"));  
    if(p!=NULL || p1 != NULL)  
    {  
        return 1 ;
    }  
    else  
    {  
        return 0 ; 
    }  
}
/**
网页内容所有获取链接
*/
static void search_for_links(GumboNode* node) {
  if (node->type != GUMBO_NODE_ELEMENT) {
    return;
  }
  GumboAttribute* href;
  if (node->v.element.tag == GUMBO_TAG_A &&
      (href = gumbo_get_attribute(&node->v.element.attributes, "href"))) {
    //std::cout << href->value << std::endl;
    
	if(isURLLink(href->value))
	{
		printf("getlink->%s\n",href->value);
		urlList.push_back(href->value);
	}
	else
	{
		//printf("err->%s\n",href->value);
	}
	
  }

  GumboVector* children = &node->v.element.children;
  for (unsigned int i = 0; i < children->length; ++i) {
    search_for_links((GumboNode*)children->data[i]);
  }
}

/**
获取网页信息
*/
string GetHtmlByWget(string url) 
{ 
    //获取待下载网页文件名 
    string fileName = url.substr((int)url.find_last_of("/") + 1); 

    if(fileName != "") 
    { 
        string strCom = "wget "; //wget命令，-q表示不显示下载信息 
        strCom.append(url); 
        system(strCom.c_str()); //执行wget 
        //cout<<fileName<<endl;
        ifstream fin(fileName.c_str()); 
        if(!fin) 
        { 
            return ""; 
        } 
        string strHtml = ""; 
        char chTemp[1024*10] = ""; 
        //读取网页文件到内存中 
        while(fin.getline(chTemp , 1024*10)) 
        { 
            strHtml.append(string(chTemp)); 
            strcpy(chTemp , ""); 
        } 
        fin.close(); 
        strCom = "rm -f "; //删除文件命令,-f表示直接删除不做任何提示 
        strCom.append(fileName); 
        system(strCom.c_str()); //删除刚才下载下来的文件 
        return strHtml; //返回网页源码 
    } 
    else 
    { 
        return ""; 
    } 
}
/**
开始跑数据
*/
void run()
{
	while( searhIndex < MAXLINKS )
	{
		cout<<"urlList len:"<<urlList.size()<<endl;
		if(searhIndex >= urlList.size())
		{
			cout<<"pacong over..."<<endl;
			return ;
		}
		string url = urlList[searhIndex];
		cout<<"get ***************** :"<<url<<endl ;
		string htmlText = GetHtmlByWget(url);
		const char * charHtmlText = getChStrByString(htmlText);
		GumboOutput *output;
		output = gumbo_parse(charHtmlText);
		/* 获取TITLE */
		search_for_links(output->root);
		/* 销毁，释放内存 */
		gumbo_destroy_output(&kGumboDefaultOptions,output);
		searhIndex++ ;
	}
}

/**
入口函数
*/
int main(int argc,char **argv)
{
	// 初始化数据
	urlList.push_back("http://www.jd.com");
	run();
}