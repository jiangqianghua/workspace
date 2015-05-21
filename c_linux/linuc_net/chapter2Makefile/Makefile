CFLAGS = -Iadd -Isub -O2

VPATH=add:sub
OBJS = add_int.o add_float.o sub_int.o sub_float.o main.o
TARGET = cacu1

$(TARGET):$(OBJS)
	$(CC) -o $(TARGET) $(OBJS) $(CFLAGS)

CLEAN:
	-$(RM) $(TARGET)
	-$(RM) $(OBJS)
