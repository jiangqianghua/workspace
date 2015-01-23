/*************************************************************************
	> File Name: c1_msgbuf.c
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sun 14 Dec 2014 05:09:21 AM PST
 ************************************************************************/

#include<stdio.h>
#include<linux/msg.h>
#include<sys/types.h>
//#include<sys/ipc.h>

//int send_message1(int qid , struct mymsgbuf *qbuf);
int open_queue(key_t keyval);

	struct mymsgbuf{
		long mtype ;
		int request ;
		double salary ;
	} msg ;
int main()
{
	int qid ;
	key_t msgkey ;

	msgkey = ftok(".",'m');
	if((qid = open_queue(msgkey)) == -1)
	{
		perror("open_queue") ;
		exit(1);
	}
	printf(" qid = %d" , qid) ;
	msg.mtype = 1; 
	msg.request = 1; 
	msg.salary = 1000.00;
	int result ;
	if((result = send_message1(qid,&msg)) == -1)
	{
		perror("send_message");
		exit(1);
	}
}

int open_queue(key_t keyval)
{
	int qid ;

	if((qid = msgget(keyval , IPC_CREAT|0660)) == -1)
	{
		return -1 ;
	}
	return qid ;

}

int send_message1(int qid , struct mymsgbuf *qbuf)
{
	int result , length ;
	length = sizeof(struct mymsgbuf) - sizeof(long);
	if((result = msgsnd(qid , qbuf , length,0)) == -1)
		return -1 ;
	return result ;
}
