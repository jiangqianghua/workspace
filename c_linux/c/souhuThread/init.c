#include<unistd.h>
#include<signal.h>
#include<sys/param.h>
#include<sys/types.h>
#include<sys/stat.h>
void init_daemon(void)
{
        int pid ;
        int i;
        if(pid == fork())
                exit(0); // end father thread
        else if(pid < 0 )
                exit(1); fork fail,and exit
        // the first child thread,the backstage continue;
        setsid(); // the first chid thread become new group leader
        // the first leave the control terminal
        if(pid = fork())
                exit(0);// end the first hread
        else if(pid < 0 )
                exit(1); // fork failer,exit

        // the second child thread continuel
        // hte second child thread is not group leader
        for(i = 0 ; i < NOFILE ; ++i) // close the file descriptor
        {
                close(i);
                chdir("/tmp");
