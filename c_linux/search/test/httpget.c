#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <unistd.h>
#include <netdb.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <iostream>
#include <fstream>
using namespace std;


//how run 
//>g++ -o httpget httpget.c -lgumbo
// ./httpget

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

int main(int argc,char **argv)
{
   string html = GetHtmlByWget("http://www.koo.cn"); 
   //cout<<html<<endl;
   return 0 ;
}