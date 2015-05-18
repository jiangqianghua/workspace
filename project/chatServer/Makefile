OBJS = mainServer.o tools.o
CC = gcc
CFLAGS = -Wall -O -g
mainServer:$(OBJS)
	$(CC) $(OBJS) -o mainServer -lpthread
mainServer.o: mainServer.c mainServer.h tools.h
	$(CC) $(CFLAGS) -c mainServer.c -o mainServer.o
tools.o:tools.c tools.h
	$(CC) $(CFLAGS) -c tools.c -o tools.o

clean:
	rm -rf *.o mainServer 
