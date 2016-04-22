#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <string.h>
/* 包含头文件 */
#include <gumbo.h>

//how run 
//>gcc -o test test.c -lgumbo
// ./text baidu.html
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
 get_title(output->root);
 
 /* 销毁，释放内存 */
 gumbo_destroy_output(&kGumboDefaultOptions,output);
 free(data);
 printf("end test...\n");
 return 0;
}