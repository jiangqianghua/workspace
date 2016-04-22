#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <string.h>
/* 包含头文件 */
#include <gumbo.h>

#include <iostream>
#include <fstream>
using namespace std;

//how run 
//>gcc -o links links.c -lgumbo
// ./links baidu.html

string GetHtmlByWget(string url) 
{ 
    //获取待下载网页文件名 
    string fileName = url.substr((int)url.find_last_of("/") + 1); 

    if(fileName != "") 
    { 
        string strCom = "wget -q "; //wget命令，-q表示不显示下载信息 
        strCom.append(url); 
        system(strCom.c_str()); //执行wget 
        cout<<fileName<<endl;
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
        //strCom = "rm -f "; //删除文件命令,-f表示直接删除不做任何提示 
        //strCom.append(fileName); 
        //system(strCom.c_str()); //删除刚才下载下来的文件 
        return strHtml; //返回网页源码 
    } 
    else 
    { 
        return ""; 
    } 
}

static void search_for_links(GumboNode* node) {
  if (node->type != GUMBO_NODE_ELEMENT) {
    return;
  }
  GumboAttribute* href;
  if (node->v.element.tag == GUMBO_TAG_A &&
      (href = gumbo_get_attribute(&node->v.element.attributes, "href"))) {
    //std::cout << href->value << std::endl;
    printf("getlink->%s\n",href->value);
  }

  GumboVector* children = &node->v.element.children;
  for (unsigned int i = 0; i < children->length; ++i) {
    search_for_links(children->data[i]);
  }
}

void get_title(GumboNode *node)
{
 GumboVector *children;
 int i;
 
 /* 如果当前节点不是一个元素的话直接返回 */
 if(node->type != GUMBO_NODE_ELEMENT) return;
 /* 获取该节点的所有子元素节点 */
 children=&node->v.element.children;
 
 /* 检查当前节点的标签是否为TITLE（title)
  * 如果是则输出该节点下第一个节点的文本内容 */
 if(node->v.element.tag == GUMBO_TAG_TITLE)
  printf("%s\n",((GumboNode *)children->data[0])->v.text.text);
 
 /* 递归该节点下的所有子节点 */
 for(i=0;i < children->length;++i)
  get_title(children->data[i]);
}
 
int main(int argc,char **argv)
{
 printf("start test...\n");
 struct stat buf;
 GumboOutput *output;
 FILE *fp;
 char *data;
 
 /* 读取HTML文本文件 */
 if(!(fp=fopen(argv[1],"rb"))) return -1;
 stat(argv[1],&buf);
 data=malloc(sizeof(char)*(buf.st_size+1));
 fread(data,sizeof(char),buf.st_size,fp);
 fclose(fp);
 data[buf.st_size]=0;
 
 printf("gumbo_parse...\n");
 /* 解析HTML文本文件 */
 output=gumbo_parse(data);
 printf("get_title...\n");
 /* 获取TITLE */
 search_for_links(output->root);
 
 /* 销毁，释放内存 */
 gumbo_destroy_output(&kGumboDefaultOptions,output);
 free(data);
 printf("end test...\n");
 return 0;
}