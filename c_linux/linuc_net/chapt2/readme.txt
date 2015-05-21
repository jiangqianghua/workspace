
gcc -shared -fPIC -o libstrfun.so string.o
gcc -o testso main.c -L. -lstrfun
