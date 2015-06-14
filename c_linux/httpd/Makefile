CC = gcc
CFLAGS = -Wall -g
LIBS = -lpthread
TARGET = shttpd
RM = rm -f
OBJS = shttpd_parameters.o shttpd.o
all:$(OBJS)
	$(CC) -o $(TARGET) $(OBJS) $(LIBS)
clean:
	$(CC) $(TARGET) $(OBJS)
