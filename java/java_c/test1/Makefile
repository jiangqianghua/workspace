libMyNative.so:MyNative.o
	gcc -Wall  -shared -fPIC  -o libMyNative.so MyNative.o
MyNative.o:MyNative.c MyNative.h
	gcc -Wall -fPIC -c MyNative.c -I/. -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include/linux
cl:
	rm -rf *.o *.so
