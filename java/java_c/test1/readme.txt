gcc -Wall -fPIC -c MyNative.c -I/. -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include/linux
gcc -Wall  -shared -fPIC  -o libNative.so MyNative.o