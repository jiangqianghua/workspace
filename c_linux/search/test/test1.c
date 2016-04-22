#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <oauth.h>
/* 包含头文件 */
#include <gumbo.h>
 
#define URL "http://ip.yqie.com/dns_usa.htm"
 
void print_dns(GumboNode *node,GumboAttribute *attr)
{
 /* 获取子节点 */
 GumboNode *ip=(GumboNode *)(&node->v.element.children)->data[0];
 
 /* 根据class属性的值打印结果 */
 if(strcmp(attr->value,"ipstart") == 0)
 {
  if(ip->type == GUMBO_NODE_TEXT)
   printf("开始IP:%s ",ip->v.text.text);
 }
 else if(strcmp(attr->value,"ipend") == 0)
 {
  if(ip->type == GUMBO_NODE_TEXT)
   printf("结束IP:%s ",ip->v.text.text);
 }
 else if(strcmp(attr->value,"address") == 0)
 {
  if(ip->type == GUMBO_NODE_TEXT)
   printf("物理地址:%s\n",ip->v.text.text);
 }
}
 
void get_dns(GumboNode *node,GumboTag tag)
{
 GumboVector *children;
 GumboAttribute *attr;
 int i;
 
 if(node->type != GUMBO_NODE_ELEMENT) return;
 /* 获取当前节点class属性 */
 if(attr=gumbo_get_attribute(&node->v.element.attributes,"class"))
  print_dns(node,attr);
 
 /* 当前节点子节点 */
 children=&node->v.element.children;
 /* 如果当前节点标签为td我们就查找dd标签 */
 if(node->v.element.tag == GUMBO_TAG_DT)
  for(i=0;i < children->length;++i)
   get_dns(children->data[i],GUMBO_TAG_DD);
 
 /* 查找所有<dt>标签 */
 for(i=0;i < children->length;++i)
  get_dns(children->data[i],GUMBO_TAG_DT);
}
 
int main(void)
{
 GumboOutput *output;
 char *buf;
 
 /* 下载HTML文本文件 */
 buf=oauth_http_get(URL,NULL);
 if(!buf) return-1;
 /* 解析 */
 output=gumbo_parse(buf);
 if(!output)
 {
  free(buf);
  return -1;
 }
 /* 获取我们想要的内容 <dt>*/
 get_dns(output->root,GUMBO_TAG_DT);
 
 /* 释放资源 */
 gumbo_destroy_output(&kGumboDefaultOptions,output);
 free(buf);
 
 return 0;
}