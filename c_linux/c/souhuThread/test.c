#include<stdio.h>
#include<time.h>
void init_daemon(void);

main()
{
        FILE *fp;
        time_t t;
        init_deamon(); // init daemon

        while(1)
        {
                sleep(60);
                if((fp = fopen("test.log","a"))>=0)
                {
                        t = time(0);
                        fprintf(fp,"im here at %s/n",asctime(&t));
                        fclose(fp);
                }
        }
}
